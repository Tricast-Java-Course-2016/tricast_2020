package com.tricast.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tricast.managers.UserManager;
import com.tricast.repositories.entities.User;
import com.tricast.repositories.entities.enums.UserGender;

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

    @GetMapping
    public List<User> getAll(@RequestParam("gender") UserGender gender) {
        // TODO
        return null;
    }

    @PostMapping
    public User saveUser(@RequestBody User userRequest) {
        return userManager.createUser(userRequest);
    }

}
