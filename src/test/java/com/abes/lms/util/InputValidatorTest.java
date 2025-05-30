package com.abes.lms.util;

import com.abes.lms.exception.InputValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {
    
    @Test
    void testValidateBookTitle_Valid() {
        assertDoesNotThrow(() -> InputValidator.validateBookTitle("Valid Title"));
        assertDoesNotThrow(() -> InputValidator.validateBookTitle("A"));
        assertDoesNotThrow(() -> InputValidator.validateBookTitle("A".repeat(100)));
    }
    
    @Test
    void testValidateBookTitle_Null() {
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateBookTitle(null)
        );
        assertEquals("Book title cannot be empty", exception.getMessage());
    }
    
    @Test
    void testValidateBookTitle_Empty() {
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateBookTitle("")
        );
        assertEquals("Book title cannot be empty", exception.getMessage());
    }
    
    @Test
    void testValidateBookTitle_Whitespace() {
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateBookTitle("   ")
        );
        assertEquals("Book title cannot be empty", exception.getMessage());
    }
    
    @Test
    void testValidateBookTitle_TooLong() {
        String longTitle = "A".repeat(101);
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateBookTitle(longTitle)
        );
        assertEquals("Book title cannot exceed 100 characters", exception.getMessage());
    }
    
    @Test
    void testValidateAuthor_Valid() {
        assertDoesNotThrow(() -> InputValidator.validateAuthor("Valid Author"));
        assertDoesNotThrow(() -> InputValidator.validateAuthor("A"));
        assertDoesNotThrow(() -> InputValidator.validateAuthor("A".repeat(50)));
    }
    
    @Test
    void testValidateAuthor_Null() {
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateAuthor(null)
        );
        assertEquals("Author name cannot be empty", exception.getMessage());
    }
    
    @Test
    void testValidateAuthor_Empty() {
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateAuthor("")
        );
        assertEquals("Author name cannot be empty", exception.getMessage());
    }
    
    @Test
    void testValidateAuthor_TooLong() {
        String longAuthor = "A".repeat(51);
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateAuthor(longAuthor)
        );
        assertEquals("Author name cannot exceed 50 characters", exception.getMessage());
    }
    
    @Test
    void testValidateRating_Valid() {
        assertDoesNotThrow(() -> InputValidator.validateRating(0.0));
        assertDoesNotThrow(() -> InputValidator.validateRating(2.5));
        assertDoesNotThrow(() -> InputValidator.validateRating(5.0));
    }
    
    @Test
    void testValidateRating_TooLow() {
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateRating(-0.1)
        );
        assertEquals("Rating must be between 0.0 and 5.0", exception.getMessage());
    }
    
    @Test
    void testValidateRating_TooHigh() {
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateRating(5.1)
        );
        assertEquals("Rating must be between 0.0 and 5.0", exception.getMessage());
    }
    
    @Test
    void testValidateUserName_Valid() {
        assertDoesNotThrow(() -> InputValidator.validateUserName("abc"));
        assertDoesNotThrow(() -> InputValidator.validateUserName("validuser"));
        assertDoesNotThrow(() -> InputValidator.validateUserName("A".repeat(30)));
    }
    
    @Test
    void testValidateUserName_Null() {
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateUserName(null)
        );
        assertEquals("User name cannot be empty", exception.getMessage());
    }
    
    @Test
    void testValidateUserName_Empty() {
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateUserName("")
        );
        assertEquals("User name cannot be empty", exception.getMessage());
    }
    
    @Test
    void testValidateUserName_TooShort() {
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateUserName("ab")
        );
        assertEquals("User name must be at least 3 characters long", exception.getMessage());
    }
    
    @Test
    void testValidateUserName_TooLong() {
        String longName = "A".repeat(31);
        InputValidationException exception = assertThrows(
            InputValidationException.class,
            () -> InputValidator.validateUserName(longName)
        );
        assertEquals("User name cannot exceed 30 characters", exception.getMessage());
    }
    
   
}