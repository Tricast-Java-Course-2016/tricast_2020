package com.tricast.controllers;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tricast.api.requests.UserCreationRequest;
import com.tricast.api.requests.UserLoginRequest;
import com.tricast.api.requests.UserPwdChangeRequest;
import com.tricast.api.requests.UserUpdateRequest;
import com.tricast.api.responses.UserResponse;
import com.tricast.controllers.constants.WorkingHoursConstants;
import com.tricast.managers.UserManager;
import com.tricast.managers.exceptions.WorkingHoursException;
import com.tricast.repositories.entities.User;

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
	public UserResponse createUser(@RequestBody UserCreationRequest userCreationRequest) {
		LOG.info("UserCreationRequest:" + userCreationRequest.toString());
		return userManager.createUserFromRequest(userCreationRequest);
	}

	@PutMapping(path = "/update")
	public UserResponse updateUser(@RequestBody UserUpdateRequest UserUpdateRequest) {
		LOG.info("UserUpdateRequest:" + UserUpdateRequest.toString());
		return userManager.updateUserFromRequest(UserUpdateRequest);
	}

	@GetMapping(path = "/search")
    public ResponseEntity<?> searchUser(@RequestParam("userName") String userName) {
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
	public UserResponse loginUser(@RequestBody UserLoginRequest userLoginRequest) {
		LOG.info("userLoginRequest:" + userLoginRequest.toString());
		return userManager.loginUserFromRequest(userLoginRequest);
	}

	@PutMapping(path = "/pwdc")
	public UserResponse pwdChangeUser(@RequestBody UserPwdChangeRequest userPwdChangeRequest) {
		LOG.info("UserPwdChangeRequest:" + userPwdChangeRequest.toString());
		return userManager.pwdChangeUserFromRequest(userPwdChangeRequest);
	}

}
