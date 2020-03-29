package com.tricast.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public User createUser(User userRequest) {
		return userRepository.save(userRequest);
	}

	@Override
	public User updateUser(User userRequest) {
		return userRepository.save(userRequest);
	}

	@Override
	public List<User> getAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User getUserByName(String userName) {
		List<User> listAllUser = (List<User>) userRepository.findAll();
		for (Iterator iterator = listAllUser.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			if (user.getUserName() != null && user.getUserName().equals(userName)) {
				return user;
			}

		}

		return null;
	}

	@Override
	public List<User> getAllUserByRoleId(long roleId) {
		List<User> listResult = new ArrayList<User>();
		List<User> listAllUser = (List<User>) userRepository.findAll();
		for (Iterator iterator = listAllUser.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			if (user.getRoleId() == roleId) {
				listResult.add(user);
			}

		}

		return listResult;

	}
}
