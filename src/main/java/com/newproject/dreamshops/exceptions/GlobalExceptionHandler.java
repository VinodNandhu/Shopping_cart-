package com.newproject.dreamshops.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        String message = "Access denied: This resource is restricted to administrators only. "
                + "Unauthorized access is not allowed for public users or non-admin accounts.";

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(message);
    }


}
