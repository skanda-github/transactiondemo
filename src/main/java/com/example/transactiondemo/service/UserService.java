package com.example.transactiondemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.transactiondemo.exception.CustomCheckedException;
import com.example.transactiondemo.model.UserAccount;
import com.example.transactiondemo.repository.UserRepository;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service // Marks this as a service bean
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection of the UserRepository
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ================== METHOD 1 ==================
    @Transactional // Basic transactional method that persists a user
    public void createUser(String name, String email) {
        // Create and save a new user in a transactional context
        UserAccount user = new UserAccount(name, email);
        userRepository.save(user);
    }

    // ================== METHOD 2 ==================
    @Transactional // Demonstrates rollback on an unchecked exception (RuntimeException)
    public void createUserWithError(String name, String email) {
        // Save user, then throw exception to trigger rollback
        UserAccount user = new UserAccount(name, email);
        userRepository.save(user);
        throw new RuntimeException("Simulated exception to trigger rollback");
    }

    // ================== METHOD 3 ==================
    @Transactional(rollbackFor = CustomCheckedException.class) // Rollback on a custom checked exception
    public void createUserWithCustomRollback(String name, String email) throws CustomCheckedException {
        // Save user and throw a checked exception; rollback will happen due to rollbackFor
        UserAccount user = new UserAccount(name, email);
        userRepository.save(user);
        throw new CustomCheckedException("Rollback due to business rule failure");
    }

    // ================== METHOD 4 ==================
    @Transactional(propagation = Propagation.REQUIRES_NEW) // Always starts a new transaction
    public void saveWithNewTransaction(String name, String email) {
        // Save user in a new independent transaction
        UserAccount user = new UserAccount(name, email);
        userRepository.save(user);
    }

    // ================== METHOD 5 ==================
    @Transactional(isolation = Isolation.REPEATABLE_READ) // Reads using REPEATABLE_READ isolation level
    public UserAccount findWithIsolation(Long id) {
        // Fetches a user with an isolation level to avoid phantom reads
        return userRepository.findById(id).orElse(null);
    }

    // ================== METHOD 6 ==================
    @Transactional(readOnly = true) // Optimized for read-only operations
    public UserAccount findById(Long id) {
        // Fetches user data in a read-only transaction to improve performance
        return userRepository.findById(id).orElse(null);
    }

    // ================== METHOD 7 ==================
    @Transactional(readOnly = true) // Read-only transaction for retrieving all users
    public List<UserAccount> findAllUsers() {
        // Returns a list of all users from the database
        return userRepository.findAll();
    }
}
