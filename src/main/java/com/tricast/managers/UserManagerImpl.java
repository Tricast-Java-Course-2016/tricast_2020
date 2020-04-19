package com.tricast.managers;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.UserCreationRequest;
import com.tricast.api.requests.UserLoginRequest;
import com.tricast.api.requests.UserPwdChangeRequest;
import com.tricast.api.requests.UserUpdateRequest;
import com.tricast.api.responses.UserResponse;
import com.tricast.managers.exceptions.WorkingHoursException;
import com.tricast.repositories.UserRepository;
import com.tricast.repositories.entities.User;

@Component
public class UserManagerImpl implements UserManager {

	private UserRepository userRepository;

	@Autowired
	public UserManagerImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<User> getById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public UserResponse createUserFromRequest(UserCreationRequest userCreationRequest) {
		User newUser = mapUserCreationRequestToUser(userCreationRequest);
		User createdUser = userRepository.save(newUser);
		return mapUserToUserResponse(createdUser);
	}

	@Override
	public UserResponse updateUserFromRequest(UserUpdateRequest UserUpdateRequest) {

		User userToUpdate = mapUserUpdateRequestToUser(UserUpdateRequest);
		UserResponse userResponse = null;
		if (userToUpdate != null) {
			User updatedUser = userRepository.save(userToUpdate);
			userResponse = mapUserToUserResponse(updatedUser);
		}
		return userResponse;
	}

	@Override
	public List<User> getAll() {
		return (List<User>) userRepository.findAll();
	}

	private User mapUserCreationRequestToUser(UserCreationRequest userCreationRequest) {
		User newUser = new User();
		newUser.setCompanyName(userCreationRequest.getCompanyName());
		newUser.setAddress(userCreationRequest.getAddress());
		newUser.setAccountCreated(ZonedDateTime.now());
		newUser.setDob(userCreationRequest.getDob());
		newUser.setEmail(userCreationRequest.getEmail());
		newUser.setFirstName(userCreationRequest.getFirstName());
		newUser.setGender(userCreationRequest.getGender());
		newUser.setIsActive(true);
		newUser.setLastName(userCreationRequest.getLastName());
		newUser.setMiddleName(userCreationRequest.getMiddleName());
		newUser.setPassword(userCreationRequest.getPassword());
		newUser.setPhone(userCreationRequest.getPhone());
		newUser.setPostcode(userCreationRequest.getPostcode());
		newUser.setRoleId(userCreationRequest.getRoleId());
		newUser.setUserName(userCreationRequest.getUserName());
		return newUser;
	}

	private User mapUserUpdateRequestToUser(UserUpdateRequest userUpdateRequest) {

		Optional<User> userToUpdateOptional = userRepository.findById(userUpdateRequest.getId());

		User userToUpdate = null;
		if (userToUpdateOptional.isPresent()) {
			userToUpdate = userToUpdateOptional.get();

			userToUpdate.setCompanyName(userUpdateRequest.getCompanyName());
			userToUpdate.setAddress(userUpdateRequest.getAddress());
			userToUpdate.setDob(userUpdateRequest.getDob());
			userToUpdate.setEmail(userUpdateRequest.getEmail());
			userToUpdate.setFirstName(userUpdateRequest.getFirstName());
			userToUpdate.setGender(userUpdateRequest.getGender());
			userToUpdate.setIsActive(userUpdateRequest.getIsActive());
			userToUpdate.setLastName(userUpdateRequest.getLastName());
			userToUpdate.setMiddleName(userUpdateRequest.getMiddleName());
			userToUpdate.setPassword(userUpdateRequest.getPassword());
			userToUpdate.setPhone(userUpdateRequest.getPhone());
			userToUpdate.setPostcode(userUpdateRequest.getPostcode());
			userToUpdate.setRoleId(userUpdateRequest.getRoleId());
			userToUpdate.setUserName(userUpdateRequest.getUserName());
		}
		return userToUpdate;

	}

	private UserResponse mapUserToUserResponse(User user) {
		UserResponse updatedUser = new UserResponse();
		updatedUser.setId(user.getId());
		updatedUser.setCompanyName(user.getCompanyName());
		updatedUser.setAddress(user.getAddress());
		updatedUser.setDob(user.getDob());
		updatedUser.setEmail(user.getEmail());
		updatedUser.setFirstName(user.getFirstName());
		updatedUser.setGender(user.getGender());
		updatedUser.setActive(user.getIsActive());
		updatedUser.setLastName(user.getLastName());
		updatedUser.setMiddleName(user.getMiddleName());
		updatedUser.setLastLogin(user.getLastLogin());
		updatedUser.setAccountCreated(user.getAccountCreated());
		updatedUser.setPhone(user.getPhone());
		updatedUser.setPostcode(user.getPostcode());
		updatedUser.setRoleId(user.getRoleId());
		updatedUser.setUserName(user.getUserName());

		return updatedUser;
	}

	@Override
	public UserResponse loginUserFromRequest(UserLoginRequest userLoginRequest) {
		// TODO titkositas jon majd ide
		List<User> userList = userRepository.findByUserNameAndPassword(userLoginRequest.getUserName(),
				userLoginRequest.getPassword());

		if (userList != null && userList.size() == 1) {
			User responseUser = userList.get(0);
			return mapUserToUserResponse(responseUser);
		}

		return null;
	}

	@Override
	public UserResponse searchUserFromRequest(String userName) throws WorkingHoursException {
		List<User> userList = userRepository.findByUserName(userName);

		if (userList != null && userList.size() == 1) {
			User responseUser = userList.get(0);
			return mapUserToUserResponse(responseUser);
		}

		throw new WorkingHoursException("User does not exist");
	}

	@Override
	public UserResponse pwdChangeUserFromRequest(UserPwdChangeRequest userPwdChangeRequest) {
		Optional<User> userToUpdateOptional = userRepository.findById(Long.parseLong(userPwdChangeRequest.getUserId()));
		User userToUpdate = null;
		if (userToUpdateOptional.isPresent()) {

			userToUpdate = userToUpdateOptional.get();
			if (userPwdChangeRequest.getOldPassword() != null
					&& userPwdChangeRequest.getOldPassword().equals(userToUpdate.getPassword())) {
				// TODO titkositas jon majd ide
				userToUpdate.setPassword(userPwdChangeRequest.getNewPassword());
				User updatedUser = userRepository.save(userToUpdate);
				return mapUserToUserResponse(updatedUser);
			}
		}
		return null;
	}

}
