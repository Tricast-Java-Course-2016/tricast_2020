package com.tricast.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.tricast.managers.UserManager;
import com.tricast.repositories.entities.User;

@RestController
@RequestMapping(path = "rest/users")
public class UserController {

	@Autowired
	private UserManager userManager;

	// workinghours/rest/users/{id}
	@GetMapping(path = "/{id}")
	public Optional<User> getById(@PathVariable("id") long id) {
		return userManager.getById(id);
	}

	@PostMapping(path = "/create")
	public UserResponse createUser(@RequestBody UserCreationRequest userCreationRequest) {
		return userManager.createUserFromRequest(userCreationRequest);
	}

	@PutMapping(path = "/update")
	public UserResponse updateUser(@RequestBody UserUpdateRequest UserUpdateRequest) {
		return userManager.updateUserFromRequest(UserUpdateRequest);
	}

	@GetMapping(path = "/search")
	public UserResponse searchUser(@RequestParam("userName") String userName) {
		return userManager.searchUserFromRequest(userName);
	}

	@PostMapping(path = "/login")
	public UserResponse loginUser(@RequestBody UserLoginRequest userLoginRequest) {
		return userManager.loginUserFromRequest(userLoginRequest);
	}

	@PutMapping(path = "/pwdc")
	public UserResponse pwdChangeUser(@RequestBody UserPwdChangeRequest userPwdChangeRequest) {
		return userManager.pwdChangeUserFromRequest(userPwdChangeRequest);
	}

}
