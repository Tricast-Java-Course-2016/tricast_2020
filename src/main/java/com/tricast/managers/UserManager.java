package com.tricast.managers;

import java.util.List;
import java.util.Optional;

import com.tricast.repositories.entities.User;

public interface UserManager {

	Optional<User> getById(long id);

	User createUser(User userRequest);

	User updateUser(User userRequest);

	List<User> getAll();

	User getUserByName(String userName);

	List<User> getAllUserByRoleId(long roleId);

}
