package com.abes.lms.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class BookDTOTest {
    
    private BookDTO book;
    
    @BeforeEach
    void setUp() {
        book = new BookDTO("Test Book", "Test Author", 1, 4.5);
    }
    
    @Test
    void testDefaultConstructor() {
        BookDTO defaultBook = new BookDTO();
        assertTrue(defaultBook.isAvailable());
        assertNull(defaultBook.getBorrowedBy());
    }
    
    @Test
    void testParameterizedConstructor() {
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals(1, book.getId());
        assertEquals(4.5, book.getRating());
        assertTrue(book.isAvailable());
        assertNull(book.getBorrowedBy());
    }
    
    @Test
    void testSettersAndGetters() {
        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());
        
        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());
        
        book.setId(999);
        assertEquals(999, book.getId());
        
        book.setRating(3.5);
        assertEquals(3.5, book.getRating());
        
        book.setAvailable(false);
        assertFalse(book.isAvailable());
        
        book.setBorrowedBy("John Doe");
        assertEquals("John Doe", book.getBorrowedBy());
    }
    
    @Test
    void testToStringAvailable() {
        String expected = "ID: 1 | Title: Test Book | Author: Test Author | Rating: 4.5 | Available: Yes";
        assertEquals(expected, book.toString());
    }
    
    @Test
    void testToStringBorrowed() {
        book.setAvailable(false);
        book.setBorrowedBy("John Doe");
        String expected = "ID: 1 | Title: Test Book | Author: Test Author | Rating: 4.5 | Available: No (Borrowed by: John Doe)";
        assertEquals(expected, book.toString());
    }
    
    @Test
    void testBorrowingFlow() {
        // Initially available
        assertTrue(book.isAvailable());
        assertNull(book.getBorrowedBy());
        
        // Borrow the book
        book.setAvailable(false);
        book.setBorrowedBy("Alice");
        
        assertFalse(book.isAvailable());
        assertEquals("Alice", book.getBorrowedBy());
        
        // Return the book
        book.setAvailable(true);
        book.setBorrowedBy(null);
        
        assertTrue(book.isAvailable());
        assertNull(book.getBorrowedBy());
    }
}