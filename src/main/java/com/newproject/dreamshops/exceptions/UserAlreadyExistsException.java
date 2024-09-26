package com.newproject.dreamshops.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);  // Pass the message to the superclass (RuntimeException)
    }
}
