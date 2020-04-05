package com.tricast.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tricast.repositories.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByUserNameAndPassword(String userName, String password);

	List<User> findByUserName(String userName);
}
