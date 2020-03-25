package com.tricast.managers;

import java.util.Optional;

import com.tricast.repositories.entities.User;

public interface UserManager {

    Optional<User> getById(long id);

    User createUser(User userRequest);
}
