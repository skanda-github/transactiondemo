package com.example.transactiondemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.transactiondemo.exception.CustomCheckedException;
import com.example.transactiondemo.model.UserAccount;
import com.example.transactiondemo.service.UserService;

@RestController // Marks this class as a REST API controller
public class UserController {

    private final UserService userService;

    @Autowired // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ================== ENDPOINT 1 ==================
    @PostMapping("/create-user")
    public String createUser(@RequestParam String name, @RequestParam String email) {
        // Calls service to create user in a transaction
        userService.createUser(name, email);
        return "User created successfully!";
    }

    // ================== ENDPOINT 2 ==================
    @PostMapping("/create-user-error")
    public String createUserWithError(@RequestParam String name, @RequestParam String email) {
        // Calls service method that throws a RuntimeException to simulate rollback
        try {
            userService.createUserWithError(name, email);
        } catch (Exception e) {
            return "Transaction failed and rolled back: " + e.getMessage();
        }
        return "User created successfully!";
    }

    // ================== ENDPOINT 3 ==================
    @PostMapping("/create-user-custom")
    public String createUserWithCustomRollback(@RequestParam String name, @RequestParam String email) {
        // Calls service method that throws a CustomCheckedException and rolls back
        try {
            userService.createUserWithCustomRollback(name, email);
        } catch (CustomCheckedException e) {
            return "Custom rollback occurred: " + e.getMessage();
        }
        return "User created with custom rollback handling!";
    }

    // ================== ENDPOINT 4 ==================
    @PostMapping("/create-user-new-tx")
    public String saveWithNewTransaction(@RequestParam String name, @RequestParam String email) {
        // Calls method that runs in a new transaction separate from caller
        userService.saveWithNewTransaction(name, email);
        return "User saved with REQUIRES_NEW transaction!";
    }

    // ================== ENDPOINT 5 ==================
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable Long id) {
        // Retrieves a user by ID using read-only transaction
        var user = userService.findById(id);
        return user != null ? "User found: " + user.getName() : "User not found";
    }

    // ================== ENDPOINT 6 ==================
    @GetMapping("/users")
    public List<UserAccount> getAllUsers() {
        // Fetches and returns all users in the database
        return userService.findAllUsers();
    }
}