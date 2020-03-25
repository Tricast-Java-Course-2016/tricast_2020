package com.tricast.managers;

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

}
