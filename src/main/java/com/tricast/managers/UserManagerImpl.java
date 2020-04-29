package com.tricast.managers;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	private static final Logger LOG = LogManager.getLogger(UserManagerImpl.class);

	public static final int STRING_LENGTH_60 = 60;
	public static final int STRING_LENGTH_30 = 30;
	public static final int STRING_LENGTH_100 = 100;
	public static final int STRING_LENGTH_300 = 300;
	public static final int STRING_LENGTH_6 = 6;
	public static final int STRING_LENGTH_12 = 12;
	public static final int STRING_LENGTH_50 = 50;
	public static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

	private final BCryptPasswordEncoder encoder;

	@Autowired
	public UserManagerImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
		this.encoder = new BCryptPasswordEncoder();
	}

	@Override
	public Optional<User> getById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public UserResponse createUserFromRequest(UserCreationRequest userCreationRequest) {
		User newUser = mapUserCreationRequestToUser(userCreationRequest);

		boolean validUser = validateUser(newUser);
		if (validUser) {
			newUser.setPassword(encoder.encode(userCreationRequest.getPassword()));
			User createdUser = userRepository.save(newUser);
			return mapUserToUserResponse(createdUser);
		} else {
            return null;
        }
	}

	private boolean validateDateOfBirth(String dobToValidate) {
		String dateFormatPattern = "yyyy-MM-dd";
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern(dateFormatPattern);

		try {
			LocalDate.parse(dobToValidate, fmt);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

    private boolean validateUser(User userToValidate) {
		boolean validUser = true;
        // ORSI
        // Ebből a metódusból eldobhatnál egy WorkingHoursException-t, hasonlóan mint ahogy a search metódusnál
        // csináltuk. Itt akár minden ághoz tudnál megadni külön hibaüzenetet és akkor lehetne tudni a kliens oldalon,
        // hogy melyik mezővel votl a gond.
		if (userToValidate.getFirstName().length() > STRING_LENGTH_60) {
            validUser = false;

		} else if (userToValidate.getMiddleName().length() > STRING_LENGTH_60) {
			validUser = false;

		} else if (userToValidate.getLastName().length() > STRING_LENGTH_60) {
			validUser = false;

		} else if (validateDateOfBirth(userToValidate.getDob()) == false) {
			validUser = false;

		} else if (userToValidate.getUserName().length() > STRING_LENGTH_30) {
			validUser = false;

			// TODO gender validation

		} else if (userToValidate.getEmail().matches(EMAIL_REGEX) == false) {
			validUser = false;

		} else if (userToValidate.getEmail().length() > STRING_LENGTH_100) {
			validUser = false;

		} else if (userToValidate.getAddress().length() > STRING_LENGTH_300) {
			validUser = false;

		} else if (userToValidate.getPostcode().length() > STRING_LENGTH_6) {
			validUser = false;

		} else if (userToValidate.getPhone().length() > STRING_LENGTH_12) {
			validUser = false;

		} else if (userToValidate.getCompanyName().length() > STRING_LENGTH_50) {
			validUser = false;

		} else if (userToValidate.getPassword().length() > STRING_LENGTH_60
				|| userToValidate.getPassword().length() == 0) {
			validUser = false;
		}

		LOG.info("validateUser:" + validUser);
		return validUser;
	}

	@Override
    public UserResponse updateUserFromRequest(UserUpdateRequest UserUpdateRequest) {

		User userToUpdate = mapUserUpdateRequestToUser(UserUpdateRequest);
		boolean validUser = validateUser(userToUpdate);
		if (validUser) {
			userToUpdate.setPassword(encoder.encode(UserUpdateRequest.getPassword()));
			UserResponse userResponse = null;
			if (userToUpdate != null) {
				User updatedUser = userRepository.save(userToUpdate);
				userResponse = mapUserToUserResponse(updatedUser);
			}
			return userResponse;

		} else {
            return null;
        }

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
		LOG.info("loginUserFromRequest: user name - " + userLoginRequest.getUserName());

		List<User> userList = userRepository.findByUserName(userLoginRequest.getUserName());
		if (userList != null && userList.size() == 1) {
			User responseUser = userList.get(0);

			boolean passWordMatch = encoder.matches(userLoginRequest.getPassword(), responseUser.getPassword());

			if (passWordMatch) {
				responseUser.setLastLogin(ZonedDateTime.now());
				userRepository.save(responseUser);
				return mapUserToUserResponse(responseUser);
			}

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

			boolean passWordMatch = encoder.matches(userPwdChangeRequest.getOldPassword(), userToUpdate.getPassword());

			if (passWordMatch) {
				userToUpdate.setPassword(encoder.encode(userPwdChangeRequest.getNewPassword()));
				User updatedUser = userRepository.save(userToUpdate);
				return mapUserToUserResponse(updatedUser);
			}
		}
		return null;
	}

}
