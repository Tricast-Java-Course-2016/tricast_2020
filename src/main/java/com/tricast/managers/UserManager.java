package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.api.requests.UserCreationRequest;
import com.tricast.api.requests.UserLoginRequest;
import com.tricast.api.requests.UserPwdChangeRequest;
import com.tricast.api.requests.UserUpdateRequest;
import com.tricast.api.responses.UserResponse;
import com.tricast.managers.exceptions.WorkingHoursException;
import com.tricast.repositories.entities.User;

public interface UserManager {

	Optional<User> getById(long id);

	List<User> getAll();

	UserResponse createUserFromRequest(UserCreationRequest userCreationRequest);

	UserResponse updateUserFromRequest(UserUpdateRequest userUpdateRequest);

	UserResponse loginUserFromRequest(UserLoginRequest userLoginRequest);

    UserResponse searchUserFromRequest(String userName) throws WorkingHoursException;

	UserResponse pwdChangeUserFromRequest(UserPwdChangeRequest userPwdChangeRequest);

}
