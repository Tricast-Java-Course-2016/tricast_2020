package com.tricast.managers;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricast.api.requests.UserCreationRequest;
import com.tricast.api.responses.UserCreationResponse;
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
    public UserCreationResponse createUserFromRequest(UserCreationRequest userCreationRequest) {
        User newUser = mapUserCreationRequestToUser(userCreationRequest);
        User createdUser = userRepository.save(newUser);
        return mapUserToUserCreationResponse(createdUser);
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
        for (User user : listAllUser) {
            if (user.getUserName() != null && user.getUserName().equals(userName)) {
                return user;
            }

        }

        return null;
    }

    @Override
    public List<User> getAllUserByRoleId(long roleId) {
        List<User> listResult = new ArrayList<>();
        List<User> listAllUser = (List<User>) userRepository.findAll();
        for (Iterator<User> iterator = listAllUser.iterator(); iterator.hasNext();) {
            User user = iterator.next();
            if (user.getRoleId() == roleId) {
                listResult.add(user);
            }

        }

        return listResult;

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

    private UserCreationResponse mapUserToUserCreationResponse(User user) {
        UserCreationResponse createdUser = new UserCreationResponse();
        createdUser.setId(user.getId());
        createdUser.setCompanyName(user.getCompanyName());
        createdUser.setAddress(user.getAddress());
        createdUser.setAccountCreated(user.getAccountCreated());
        createdUser.setDob(user.getDob());
        createdUser.setEmail(user.getEmail());
        createdUser.setFirstName(user.getFirstName());
        createdUser.setGender(user.getGender());
        createdUser.setIsActive(user.getIsActive());
        createdUser.setLastName(user.getLastName());
        createdUser.setMiddleName(user.getMiddleName());
        createdUser.setPhone(user.getPhone());
        createdUser.setPostcode(user.getPostcode());
        createdUser.setRoleId(user.getRoleId());
        createdUser.setUserName(user.getUserName());
        return createdUser;
    }
}
