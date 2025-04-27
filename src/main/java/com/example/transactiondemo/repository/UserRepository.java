package com.example.transactiondemo.repository;

import com.example.transactiondemo.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marks this as a repository bean
public interface UserRepository extends JpaRepository<UserAccount, Long> {
    // Custom query methods can be added here if needed
}
