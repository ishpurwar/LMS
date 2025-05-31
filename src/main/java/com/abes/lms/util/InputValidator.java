package com.abes.lms.util;

import com.abes.lms.exception.InputValidationException;

public class InputValidator {
    
    public static void validateBookTitle(String title) throws InputValidationException {
        if (title == null || title.trim().isEmpty()) {
            throw new InputValidationException("Book title cannot be empty");
        }
        if (title.length() > 100) {
            throw new InputValidationException("Book title cannot exceed 100 characters");
        }
    }

    public static void validateAuthor(String author) throws InputValidationException {
        if (author == null || author.trim().isEmpty()) {
            throw new InputValidationException("Author name cannot be empty");
        }
        if (author.length() > 50) {
            throw new InputValidationException("Author name cannot exceed 50 characters");
        }
        if (!author.matches(".*[a-zA-Z].*")) {
            throw new InputValidationException("Author name cannot be a numerical value");
        }
    }

    public static void validateRating(double rating) throws InputValidationException {
        if (rating < 0.0 || rating > 5.0) {
            throw new InputValidationException("Rating must be between 0.0 and 5.0");
        }
    }

    public static void validateUserName(String name) throws InputValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new InputValidationException("User name cannot be empty");
        }
        if (name.length() < 3) {
            throw new InputValidationException("User name must be at least 3 characters long");
        }
        if (name.length() > 30) {
            throw new InputValidationException("User name cannot exceed 30 characters");
        }
        if (!name.matches(".*[a-zA-Z].*")) {
            throw new InputValidationException("User name cannot be a numerical value");
        }
    }

    public static void validatePassword(String password) throws InputValidationException {
        if (password == null || password.trim().isEmpty()) {
            throw new InputValidationException("Password cannot be empty");
        }
        if (password.length() < 4) {
            throw new InputValidationException("Password must be at least 4 characters long");
        }
    }
}
