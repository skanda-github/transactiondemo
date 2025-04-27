package com.example.transactiondemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.transactiondemo.model.UserAccount;
import com.example.transactiondemo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service // Marks this as a service bean
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection of the UserRepository
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Example method to create a new user
    @Transactional // This ensures the method runs within a transaction
    public void createUser(String name, String email) {
        UserAccount user = new UserAccount(name, email);
        userRepository.save(user); // Save the user in the database
    }

    // Example method to find a user by id
    public UserAccount findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
