package com.tricast.controllers;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tricast.api.requests.UserCreationRequest;
import com.tricast.api.requests.UserLoginRequest;
import com.tricast.api.requests.UserPwdChangeRequest;
import com.tricast.api.requests.UserUpdateRequest;
import com.tricast.api.responses.UserResponse;
import com.tricast.controllers.constants.WorkingHoursConstants;
import com.tricast.controllers.filters.AuthenticationSettings;
import com.tricast.managers.UserManager;
import com.tricast.managers.exceptions.WorkingHoursException;
import com.tricast.repositories.entities.User;
import com.tricast.repositories.entities.enums.Role;

@RestController
@RequestMapping(path = "rest/users")
public class UserController {

	private static final Logger LOG = LogManager.getLogger(UserController.class);

	@Autowired
	private UserManager userManager;

	// workinghours/rest/users/{id}
	@GetMapping(path = "/{id}")
	public Optional<User> getById(@PathVariable("id") long id) {
		return userManager.getById(id);
	}

	@PostMapping(path = "/create")
	public ResponseEntity<?> createUser(@RequestAttribute("authentication.roleId") int roleId,
			@RequestBody UserCreationRequest userCreationRequest) {
		LOG.info("UserCreationRequest:" + userCreationRequest.toString());
		if (Role.getById(roleId) != Role.ADMIN) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
		}

		try {
			return ResponseEntity.ok(userManager.createUserFromRequest(userCreationRequest));
		} catch (WorkingHoursException e) {
			return ResponseEntity.status(WorkingHoursConstants.APPLICATION_ERROR_RESPONSE_CODE).body(e.getMessage());
		}
	}

	@PutMapping(path = "/update")
	public ResponseEntity<?> updateUser(@RequestAttribute("authentication.roleId") int roleId,
			@RequestBody UserUpdateRequest UserUpdateRequest) {
		LOG.info("UserUpdateRequest:" + UserUpdateRequest.toString());
		// ORSI
		// Most egy üres null response-t adsz vissza 200-as response code-dal ha
		// sikertelen a user request validálás,
		// ehelyett jobb lenne egy error response-al visszatérni, ami tartalmaz egy
		// megfelelő hibaüzenetet.
		LOG.info("Role id tokenből: " + Role.getById(roleId));
		if (Role.getById(roleId) != Role.ADMIN) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
		}

		try {
			return ResponseEntity.ok(userManager.updateUserFromRequest(UserUpdateRequest));
		} catch (WorkingHoursException e) {
			return ResponseEntity.status(WorkingHoursConstants.APPLICATION_ERROR_RESPONSE_CODE).body(e.getMessage());
		} catch (Exception e) {
			LOG.error("Exception at updateUser: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

//	@GetMapping(path = "/search")
//	public ResponseEntity<?> searchUser(@RequestParam("userName") String userName) {
//
//		try {
//			return ResponseEntity.ok(userManager.searchUserFromRequest(userName));
//		} catch (WorkingHoursException e) {
//			return ResponseEntity.status(WorkingHoursConstants.APPLICATION_ERROR_RESPONSE_CODE).body(e.getMessage());
//		} catch (Exception e) {
//			LOG.error("Exception at searchUser: ", e);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//	}

	// AKOS
	@GetMapping(path = "/search")
	public ResponseEntity<?> searchUser(@RequestAttribute("authentication.roleId") int roleId,
			@RequestParam("userName") String userName) {
		LOG.info("Search user:" + userName);
		if (Role.getById(roleId) != Role.ADMIN) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
		}

		try {
			return ResponseEntity.ok(userManager.searchUserFromRequest(userName));
		} catch (WorkingHoursException e) {
			return ResponseEntity.status(WorkingHoursConstants.APPLICATION_ERROR_RESPONSE_CODE).body(e.getMessage());
		} catch (Exception e) {
			LOG.error("Excetion at searchUser: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(path = "/login")
	public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
		UserResponse response = userManager.loginUserFromRequest(userLoginRequest);
		if (response == null) {
			return null;
		}

		String token = issueToken(response.getId(), response.getUserName(), response.getRoleId());
		HttpHeaders header = buildAuthorizationHeader(token);

		return ResponseEntity.ok().headers(header).body(response);
	}

	private HttpHeaders buildAuthorizationHeader(String token) {
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + token);
		return header;
	}

	private String issueToken(long userId, String username, long roleId) {
		OffsetDateTime exp = OffsetDateTime.now().plusHours(AuthenticationSettings.TOKEN_EXPIRE_TIME_IN_HOURS);
		Algorithm algorithm = Algorithm.HMAC256(AuthenticationSettings.SECRET_KEY);

		return JWT.create().withIssuer(AuthenticationSettings.ISSUER).withExpiresAt(Date.from(exp.toInstant()))
				.withClaim(AuthenticationSettings.CLAIM_USER_IDENTIFIER, userId)
				.withClaim(AuthenticationSettings.CLAIM_USERNAME_IDENTIFIER, username)
				.withClaim(AuthenticationSettings.CLAIM_ROLE_IDENTIFIER, roleId).sign(algorithm);
	}

	@PutMapping(path = "/pwdc")
	public UserResponse pwdChangeUser(@RequestBody UserPwdChangeRequest userPwdChangeRequest) {
		LOG.info("UserPwdChangeRequest:" + userPwdChangeRequest.toString());

		UserResponse userResponse = userManager.pwdChangeUserFromRequest(userPwdChangeRequest);
		LOG.info("UserPwdChangeRequest response:" + userResponse);
		return userResponse;
	}

}
