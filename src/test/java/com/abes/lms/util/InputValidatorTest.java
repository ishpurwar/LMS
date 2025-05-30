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
    
   
}