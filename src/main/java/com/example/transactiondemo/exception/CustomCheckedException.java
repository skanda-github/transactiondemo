package com.example.transactiondemo.exception;

// Custom checked exception for demonstrating rollback
public class CustomCheckedException extends Exception{
    public CustomCheckedException(String message){
        super(message);
    }
}
