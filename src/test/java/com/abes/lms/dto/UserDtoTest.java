package com.abes.lms.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class UserDtoTest {
    
    private UserDto user;
    private BookDTO book1;
    private BookDTO book2;
    
    @BeforeEach
    void setUp() {
        user = new UserDto("testuser", "password123");
        book1 = new BookDTO("Book One", "Author One", 1, 4.0);
        book2 = new BookDTO("Book Two", "Author Two", 2, 4.5);
    }
    
    @Test
    void testDefaultConstructor() {
        UserDto defaultUser = new UserDto();
        assertNotNull(defaultUser.getBorrowedBooks());
        assertTrue(defaultUser.getBorrowedBooks().isEmpty());
    }
    
    @Test
    void testParameterizedConstructor() {
        assertEquals("testuser", user.getName());
        assertEquals("password123", user.getPassword());
        assertNotNull(user.getBorrowedBooks());
        assertTrue(user.getBorrowedBooks().isEmpty());
    }
    
    @Test
    void testSettersAndGetters() {
        user.setName("newname");
        assertEquals("newname", user.getName());
        
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
        
        List<BookDTO> books = new ArrayList<>();
        books.add(book1);
        user.setBorrowedBooks(books);
        assertEquals(1, user.getBorrowedBooks().size());
        assertEquals(book1, user.getBorrowedBooks().get(0));
    }
    
    @Test
    void testAddBorrowedBook() {
        user.addBorrowedBook(book1);
        assertEquals(1, user.getBorrowedBooks().size());
        assertTrue(user.getBorrowedBooks().contains(book1));
        
        user.addBorrowedBook(book2);
        assertEquals(2, user.getBorrowedBooks().size());
        assertTrue(user.getBorrowedBooks().contains(book1));
        assertTrue(user.getBorrowedBooks().contains(book2));
    }
    
    @Test
    void testRemoveBorrowedBook() {
        user.addBorrowedBook(book1);
        user.addBorrowedBook(book2);
        assertEquals(2, user.getBorrowedBooks().size());
        
        user.removeBorrowedBook(book1);
        assertEquals(1, user.getBorrowedBooks().size());
        assertFalse(user.getBorrowedBooks().contains(book1));
        assertTrue(user.getBorrowedBooks().contains(book2));
        
        user.removeBorrowedBook(book2);
        assertTrue(user.getBorrowedBooks().isEmpty());
    }
    
    @Test
    void testRemoveNonExistentBook() {
        user.addBorrowedBook(book1);
        assertEquals(1, user.getBorrowedBooks().size());
        
        // Try to remove a book that wasn't borrowed
        user.removeBorrowedBook(book2);
        assertEquals(1, user.getBorrowedBooks().size());
        assertTrue(user.getBorrowedBooks().contains(book1));
    }
    
    @Test
    void testToStringNoBorrowedBooks() {
        String expected = "User: testuser | Borrowed Books: 0";
        assertEquals(expected, user.toString());
    }
    
    @Test
    void testToStringWithBorrowedBooks() {
        user.addBorrowedBook(book1);
        user.addBorrowedBook(book2);
        String expected = "User: testuser | Borrowed Books: 2";
        assertEquals(expected, user.toString());
    }
    
    @Test
    void testBorrowingWorkflow() {
        // Start with no borrowed books
        assertTrue(user.getBorrowedBooks().isEmpty());
        
        // Borrow first book
        user.addBorrowedBook(book1);
        assertEquals(1, user.getBorrowedBooks().size());
        
        // Borrow second book
        user.addBorrowedBook(book2);
        assertEquals(2, user.getBorrowedBooks().size());
        
        // Return first book
        user.removeBorrowedBook(book1);
        assertEquals(1, user.getBorrowedBooks().size());
        assertTrue(user.getBorrowedBooks().contains(book2));
        
        // Return second book
        user.removeBorrowedBook(book2);
        assertTrue(user.getBorrowedBooks().isEmpty());
    }
}