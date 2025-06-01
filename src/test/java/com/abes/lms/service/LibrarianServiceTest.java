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
    @DisplayName("Should authenticate librarian with correct credentials")
    void testAuthenticateLibrarianSuccess() {
        boolean authenticated = librarianService.authenticateLibrarian("admin", "admin123");
        assertTrue(authenticated, "Should authenticate librarian with correct credentials");
    }

    @Test
    @DisplayName("Should fail authentication with wrong username")
    void testAuthenticateLibrarianWrongUsername() {
        boolean authenticated = librarianService.authenticateLibrarian("wrongadmin", "admin123");
        assertFalse(authenticated, "Should not authenticate with wrong username");
    }

    @Test
    @DisplayName("Should fail authentication with wrong password")
    void testAuthenticateLibrarianWrongPassword() {
        boolean authenticated = librarianService.authenticateLibrarian("admin", "wrongpassword");
        assertFalse(authenticated, "Should not authenticate with wrong password");
    }

    @Test
    @DisplayName("Should fail authentication with null credentials")
    void testAuthenticateLibrarianNullCredentials() {
        assertFalse(librarianService.authenticateLibrarian(null, "admin123"), 
                   "Should not authenticate with null username");
        assertFalse(librarianService.authenticateLibrarian("admin", null), 
                   "Should not authenticate with null password");
        assertFalse(librarianService.authenticateLibrarian(null, null), 
                   "Should not authenticate with null credentials");
    }

    @Test
    @DisplayName("Should add book successfully with valid data")
    void testAddBookSuccess() throws InputValidationException, BookNotFoundException {
        String title = "New Test Book";
        String author = "Test Author";
        double rating = 4.5;

        assertDoesNotThrow(() -> {
            librarianService.addBook(title, author, rating);
        }, "Should not throw exception when adding valid book");

        assertTrue(librarianService.isBookPresent(title), "Added book should be present");
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
    @DisplayName("Should throw exception when adding book with empty author")
    void testAddBookEmptyAuthor() {
        assertThrows(InputValidationException.class, () -> {
            librarianService.addBook("Title", "", 4.0);
        }, "Should throw InputValidationException for empty author");

        assertThrows(InputValidationException.class, () -> {
            librarianService.addBook("Title", null, 4.0);
        }, "Should throw InputValidationException for null author");
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
        }, "Should throw InputValidationException for duplicate book");
    }

    @Test
    @DisplayName("Should handle title length validation")
    void testAddBookTitleLength() {
        String longTitle = "A".repeat(101); // 101 characters
        String validAuthor = "Author";
        double validRating = 4.0;

        assertThrows(InputValidationException.class, () -> {
            librarianService.addBook(longTitle, validAuthor, validRating);
        }, "Should throw InputValidationException for title exceeding 100 characters");
    }

    @Test
    @DisplayName("Should handle author length validation")
    void testAddBookAuthorLength() {
        String validTitle = "Title";
        String longAuthor = "A".repeat(51); // 51 characters
        double validRating = 4.0;

        assertThrows(InputValidationException.class, () -> {
            librarianService.addBook(validTitle, longAuthor, validRating);
        }, "Should throw InputValidationException for author exceeding 50 characters");
    }

    @Test
    @DisplayName("Should check book presence correctly")
    void testIsBookPresent() throws BookAlreadyExistException,InputValidationException {
        String title = "Presence Test Book";
        
        // Book should not be present initially
        assertFalse(librarianService.isBookPresent(title), "Book should not be present initially");
        
        // Add book
        librarianService.addBook(title, "Author", 4.0);
        
        // Book should be present now
        assertTrue(librarianService.isBookPresent(title), "Book should be present after adding");
        
        // Test case insensitive
        assertTrue(librarianService.isBookPresent(title.toLowerCase()), "Should be case insensitive");
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
    void testRemoveBookSuccess() throws InputValidationException, BookNotFoundException , BookAlreadyExistException {
        String title = "Remove Test Book";
        
        // Add book first
        librarianService.addBook(title, "Author", 4.0);
        assertTrue(librarianService.isBookPresent(title), "Book should be present before removal");
        
        // Remove book
        assertDoesNotThrow(() -> {
            librarianService.removeBook(title);
        }, "Should not throw exception when removing existing book");
        
        assertFalse(librarianService.isBookPresent(title), "Book should not be present after removal");
    }

    @Test
    @DisplayName("Should throw exception when removing non-existent book")
    void testRemoveNonExistentBook() {
        assertThrows(BookNotFoundException.class, () -> {
            librarianService.removeBook("Non-existent Book");
        }, "Should throw BookNotFoundException when removing non-existent book");
    }

    @Test
    @DisplayName("Should handle edge cases for rating validation")
    void testRatingEdgeCases() throws InputValidationException {
        // Test boundary values
        assertDoesNotThrow(() -> {
            librarianService.addBook("Rating 0.0", "Author", 0.0);
        }, "Should accept rating 0.0");
        
        assertDoesNotThrow(() -> {
            librarianService.addBook("Rating 5.0", "Author", 5.0);
        }, "Should accept rating 5.0");
        
        // Test just outside boundaries
        assertThrows(InputValidationException.class, () -> {
            librarianService.addBook("Rating -0.1", "Author", -0.1);
        }, "Should reject rating -0.1");
        
        assertThrows(InputValidationException.class, () -> {
            librarianService.addBook("Rating 5.1", "Author", 5.1);
        }, "Should reject rating 5.1");
    }

    @Test
    @DisplayName("Should maintain data consistency across operations")
    void testDataConsistency() throws InputValidationException, BookNotFoundException, BookAlreadyExistException {
        String title1 = "Consistency Test 1";
        String title2 = "Consistency Test 2";
        
        // Add multiple books
        librarianService.addBook(title1, "Author1", 4.0);
        librarianService.addBook(title2, "Author2", 4.5);
        
        // Verify both are present
        assertTrue(librarianService.isBookPresent(title1), "First book should be present");
        assertTrue(librarianService.isBookPresent(title2), "Second book should be present");
        
        // Remove one book
        librarianService.removeBook(title1);
        
        // Verify only one remains
        assertFalse(librarianService.isBookPresent(title1), "First book should be removed");
        assertTrue(librarianService.isBookPresent(title2), "Second book should still be present");
        
        // Verify count is correct
        List<BookDTO> books = librarianService.showAllBooks();
        long customBooksCount = books.stream()
                .filter(book -> book.getTitle().startsWith("Consistency Test"))
                .count();
        assertEquals(1, customBooksCount, "Should have only one custom test book remaining");
    }
}