package com.example.transactiondemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.transactiondemo.service.UserService;

@RestController // Marks this as a REST controller
public class UserController {

    private final UserService userService;

    // Constructor injection of the UserService
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to create a new user
    @PostMapping("/create-user")
    public String createUser(@RequestParam String name, @RequestParam String email) {
        userService.createUser(name, email);
        return "User created successfully!";
    }

    // Endpoint to find a user by id
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable Long id) {
        var user = userService.findById(id);
        return user != null ? "User found: " + user.getName() : "User not found";
    }
}
