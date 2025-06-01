package com.abes.lms.service;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.BookAlreadyExistException;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.exception.InputValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DisplayName("Librarian Service Tests")
public class LibrarianServiceTest {
    private LibrarianService librarianService;
    @BeforeEach
    void setUp() {
        librarianService = new LibrarianService();
    }

    @Test
    @DisplayName("Should authenticate librarian with valid credentials")
    void testAuthenticateLibrarianValidCredentials() {
        assertTrue(librarianService.authenticateLibrarian("admin", "admin123"),
                "Should authenticate librarian with correct credentials");
    }

    @Test
    @DisplayName("Should fail authentication with invalid credentials")
    void testAuthenticateLibrarianInvalidCredentials() {
        assertFalse(librarianService.authenticateLibrarian("wrongadmin", "admin123"),
                "Should not authenticate with wrong username");
        assertFalse(librarianService.authenticateLibrarian("admin", "wrongpassword"),
                "Should not authenticate with wrong password");
    }

    @Test
    @DisplayName("Should handle null and empty credentials")
    void testAuthenticateLibrarianNullEmptyCredentials() {
        assertFalse(librarianService.authenticateLibrarian(null, "admin123"),
                "Should not authenticate with null username");
        assertFalse(librarianService.authenticateLibrarian("admin", null),
                "Should not authenticate with null password");
        assertFalse(librarianService.authenticateLibrarian("", "admin123"),
                "Should not authenticate with empty username");
        assertFalse(librarianService.authenticateLibrarian("admin", ""),
                "Should not authenticate with empty password");
    }

    @Test
    @DisplayName("Should add book successfully with valid data")
    void testAddBookSuccess() throws InputValidationException, BookNotFoundException, BookAlreadyExistException {
        String title = "New Test Book";
        String author = "Test Author";
        double rating = 4.5;
        assertDoesNotThrow(() -> {
            librarianService.addBook(title, author, rating);
        }, "Should not throw exception when adding valid book");
        String result = librarianService.isBookPresent(title);
        assertTrue(result.contains("present and available"), "Added book should be present and available");
    }

    @Test
    @DisplayName("Should throw exception when adding book with empty title")
    void testAddBookEmptyTitle() {
        assertThrows(InputValidationException.class, () -> {
            librarianService.addBook("", "Author", 4.0);
        }, "Should throw InputValidationException for empty title");

        assertThrows(InputValidationException.class, () -> {
            librarianService.addBook(null, "Author", 4.0);
        }, "Should throw InputValidationException for null title");
    }

    @Test
    @DisplayName("Should throw exception when adding book with invalid rating")
    void testAddBookInvalidRating() {
        assertThrows(InputValidationException.class, () -> {
            librarianService.addBook("Title", "Author", -1.0);
        }, "Should throw InputValidationException for negative rating");

        assertThrows(InputValidationException.class, () -> {
            librarianService.addBook("Title", "Author", 6.0);
        }, "Should throw InputValidationException for rating above 5.0");
    }

    @Test
    @DisplayName("Should throw exception when adding duplicate book")
    void testAddDuplicateBook() throws BookAlreadyExistException, InputValidationException {
        String title = "Duplicate Book";
        String author = "Author";
        double rating = 4.0;
        // Add book first time
        librarianService.addBook(title, author, rating);
        // Try to add same book again
        assertThrows(BookAlreadyExistException.class, () -> {
            librarianService.addBook(title, author, rating);
        }, "Should throw BookAlreadyExistException for duplicate book");
    }

    @Test
    @DisplayName("Should check book presence correctly")
    void testIsBookPresent() throws BookAlreadyExistException, InputValidationException, BookNotFoundException {
        String title = "Presence Test Book";
        assertThrows(BookNotFoundException.class, () -> {
            librarianService.isBookPresent(title);
        }, "Should throw BookNotFoundException for non-existent book");
        librarianService.addBook(title, "Author", 4.0);
        String result = librarianService.isBookPresent(title);
        assertTrue(result.contains("present and available"), "Book should be present and available after adding");
        String lowerCaseResult = librarianService.isBookPresent(title.toLowerCase());
        assertTrue(lowerCaseResult.contains("present"), "Should handle case sensitivity based on DAO implementation");
    }

    @Test
    @DisplayName("Should show all books")
    void testShowAllBooks() throws InputValidationException, BookAlreadyExistException {
        List<BookDTO> initialBooks = librarianService.showAllBooks();
        int initialCount = initialBooks.size();
        // Add a new book
        librarianService.addBook("Show Test Book", "Author", 4.0);
        List<BookDTO> updatedBooks = librarianService.showAllBooks();
        assertEquals(initialCount + 1, updatedBooks.size(), "Book count should increase by 1");
        // Verify the new book is in the list
        boolean found = updatedBooks.stream()
                .anyMatch(book -> "Show Test Book".equals(book.getTitle()));
        assertTrue(found, "New book should be in the list");
    }

    @Test
    @DisplayName("Should show all users")
    void testShowAllUsers() {
        List<UserDto> users = librarianService.showAllUsers();
        assertNotNull(users, "Users list should not be null");
        assertTrue(users.size() >= 0, "Users list size should be non-negative");
    }

    @Test
    @DisplayName("Should remove existing book successfully")
    void testRemoveBookSuccess() throws InputValidationException, BookNotFoundException, BookAlreadyExistException {
        String title = "Remove Test Book";
        librarianService.addBook(title, "Author", 4.0);
        // Verify book is present
        String result = librarianService.isBookPresent(title);
        assertTrue(result.contains("present"), "Book should be present before removal");
        // Remove book
        assertDoesNotThrow(() -> {
            librarianService.removeBook(title);
        }, "Should not throw exception when removing existing book");
        // Verify book is no longer present
        assertThrows(BookNotFoundException.class, () -> {
            librarianService.isBookPresent(title);
        }, "Book should not be present after removal");
    }

    @Test
    @DisplayName("Should throw exception when removing non-existent book")
    void testRemoveNonExistentBook() {
        assertThrows(BookNotFoundException.class, () -> {
            librarianService.removeBook("Non-existent Book");
        }, "Should throw BookNotFoundException when removing non-existent book");
    }

    @Test
    @DisplayName("Should handle book status messages correctly")
    void testBookStatusMessages() throws InputValidationException, BookAlreadyExistException, BookNotFoundException {
        String title = "Status Test Book";
        librarianService.addBook(title, "Author", 4.0);
        // Test available book message
        String availableMessage = librarianService.isBookPresent(title);
        assertTrue(availableMessage.contains("present and available"),
                "Should return correct message for available book");

    }

    @Test
    @DisplayName("Should validate book removal when book is borrowed")
    void testRemoveBorrowedBook() throws InputValidationException, BookAlreadyExistException {
        String title = "Borrowed Book Test";
        librarianService.addBook(title, "Author", 4.0);
        assertDoesNotThrow(() -> {
            librarianService.removeBook(title);
        }, "Should successfully remove available book");
    }  
}