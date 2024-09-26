package com.newproject.dreamshops.security.JWT;

// Custom exception to handle invalid JWT tokens
class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
