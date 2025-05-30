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
    
    
}