package com.abes.lms.util;

import com.abes.lms.exception.InputValidationException;

public class InputValidator {
    
    /* Input Validation for Book Title:
    1) Book title can't be 0 length
    2) Book title can't exceed length of 100 */

    public static void validateBookTitle(String title) throws InputValidationException {
        if (title == null || title.trim().isEmpty()) {
            throw new InputValidationException("Book title cannot be empty");
        }
        if (title.length() > 100) {
            throw new InputValidationException("Book title cannot exceed 100 characters");
        }
    }

    /*Input Validation for Author Name (Author)
    1) Author Name can't be NULL
    2) Author Name can't exceed 50 characters
    3) Author name cannot be a numerical value*/

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

    // Rating should be be a decimal value and should be between 0.0 and 5.0
    public static void validateRating(double rating) throws InputValidationException {
        if (rating < 0.0 || rating > 5.0) {
            throw new InputValidationException("Rating must be between 0.0 and 5.0");
        }
    }

    /*  Input Validation for User Name:
     1) User name cannot be Empty or NULL
     2) User name must be at least 3 characters long
     3) User name cannot exceed 30 characters
     4) User name cannot be a Numerical value
    */
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

    /* Input Validation for Password:
     1) pwd cannot be NULL or Empty
     2) Password must be at least 4 characters long.
    */
    public static void validatePassword(String password) throws InputValidationException {
        if (password == null || password.trim().isEmpty()) {
            throw new InputValidationException("Password cannot be empty");
        }
        if (password.length() < 4) {
            throw new InputValidationException("Password must be at least 4 characters long");
        }
    }
}
