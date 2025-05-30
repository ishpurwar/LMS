package com.abes.lms.util;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class CollectionUtilTest {

    private BookDTO testBook;
    private UserDto testUser;

    @BeforeEach
    void setUp() {
        testBook = new BookDTO("Test Book", "Test Author", 999, 4.0);
        testUser = new UserDto("testuser", "testpass");
    }

    @Test
    void testGetAllBooks_ReturnsNonEmptyList() {
        List<BookDTO> books = CollectionUtil.getAllBooks();
        assertNotNull(books);
        assertFalse(books.isEmpty());

        assertTrue(books.size() >= 4);
    }

    @Test
    void testGetAllBooks_ReturnsNewInstance() {
        List<BookDTO> books1 = CollectionUtil.getAllBooks();
        List<BookDTO> books2 = CollectionUtil.getAllBooks();
        assertNotSame(books1, books2);
    }

    @Test
    void testAddBook() {
        int initialSize = CollectionUtil.getAllBooks().size();
        CollectionUtil.addBook(testBook);

        List<BookDTO> books = CollectionUtil.getAllBooks();
        assertEquals(initialSize + 1, books.size());
        assertTrue(books.contains(testBook));
    }

    @Test
    void testRemoveBook_Exists() {
        CollectionUtil.addBook(testBook);
        assertTrue(CollectionUtil.isBookPresent("Test Book"));

        boolean removed = CollectionUtil.removeBook("Test Book");
        assertTrue(removed);
        assertFalse(CollectionUtil.isBookPresent("Test Book"));
    }

    @Test
    void testRemoveBook_NotExists() {
        boolean removed = CollectionUtil.removeBook("Non Existent Book");
        assertFalse(removed);
    }

    @Test
    void testRemoveBook_CaseInsensitive() {
        CollectionUtil.addBook(testBook);
        assertTrue(CollectionUtil.removeBook("test book"));
        assertFalse(CollectionUtil.isBookPresent("Test Book"));
    }

    @Test
    void testFindBookByTitle_Exists() {
        CollectionUtil.addBook(testBook);
        BookDTO found = CollectionUtil.findBookByTitle("Test Book");
        assertNotNull(found);
        assertEquals(testBook, found);
    }

    @Test
    void testFindBookByTitle_NotExists() {
        BookDTO found = CollectionUtil.findBookByTitle("Non Existent Book");
        assertNull(found);
    }

    @Test
    void testFindBookByTitle_CaseInsensitive() {
        CollectionUtil.addBook(testBook);
        BookDTO found = CollectionUtil.findBookByTitle("test book");
        assertNotNull(found);
        assertEquals(testBook, found);
    }

    @Test
    void testFindBookById_Exists() {
        CollectionUtil.addBook(testBook);
        BookDTO found = CollectionUtil.findBookById(999);
        assertNotNull(found);
        assertEquals(testBook, found);
    }

    @Test
    void testFindBookById_NotExists() {
        BookDTO found = CollectionUtil.findBookById(99999);
        assertNull(found);
    }

    @Test
    void testIsBookPresent_Exists() {
        CollectionUtil.addBook(testBook);
        assertTrue(CollectionUtil.isBookPresent("Test Book"));
    }

    @Test
    void testIsBookPresent_NotExists() {
        assertFalse(CollectionUtil.isBookPresent("Non Existent Book"));
    }

    @Test
    void testIsBookPresent_CaseInsensitive() {
        CollectionUtil.addBook(testBook);
        assertTrue(CollectionUtil.isBookPresent("test book"));
        assertTrue(CollectionUtil.isBookPresent("TEST BOOK"));
    }

    @Test
    void testGetAllUsers_InitiallyEmpty() {
        List<UserDto> users = CollectionUtil.getAllUsers();
        assertNotNull(users);
    }

    @Test
    void testAddUser() {
        int initialSize = CollectionUtil.getAllUsers().size();
        CollectionUtil.addUser(testUser);

        List<UserDto> users = CollectionUtil.getAllUsers();
        assertEquals(initialSize + 1, users.size());
        assertTrue(users.contains(testUser));
    }

    @Test
    void testFindUserByName_Exists() {
        CollectionUtil.addUser(testUser);
        UserDto found = CollectionUtil.findUserByName("testuser");
        assertNotNull(found);
        assertEquals(testUser, found);
    }

    @Test
    void testFindUserByName_NotExists() {
        UserDto found = CollectionUtil.findUserByName("nonexistentuser");
        assertNull(found);
    }

    @Test
    void testFindUserByName_CaseInsensitive() {
        CollectionUtil.addUser(testUser);
        UserDto found = CollectionUtil.findUserByName("TESTUSER");
        assertNotNull(found);
        assertEquals(testUser, found);
    }

    @Test
    void testIsUserExists_Exists() {
        CollectionUtil.addUser(testUser);
        assertTrue(CollectionUtil.isUserExists("testuser"));
    }

    @Test
    void testIsUserExists_NotExists() {
        assertFalse(CollectionUtil.isUserExists("nonexistentuser"));
    }

    @Test
    void testIsUserExists_CaseInsensitive() {
        CollectionUtil.addUser(testUser);
        assertTrue(CollectionUtil.isUserExists("TESTUSER"));
        assertTrue(CollectionUtil.isUserExists("TestUser"));
    }

    @Test
    void testGetNextBookId() {
        int nextId = CollectionUtil.getNextBookId();
        assertTrue(nextId > 100);

        BookDTO highIdBook = new BookDTO("High ID Book", "Author", 9999, 5.0);
        CollectionUtil.addBook(highIdBook);

        int newNextId = CollectionUtil.getNextBookId();
        assertEquals(10000, newNextId);
    }

    @Test
    void testGetNextBookId_EmptyList() {

        int nextId = CollectionUtil.getNextBookId();
        assertTrue(nextId > 0);
    }

    @Test
    void testLibrarianCredentials() {
        assertEquals("admin", CollectionUtil.LIBRARIAN_USERNAME);
        assertEquals("admin123", CollectionUtil.LIBRARIAN_PASSWORD);
    }

    @Test
    void testSampleDataExists() {
        List<BookDTO> books = CollectionUtil.getAllBooks();

        boolean hasJavaBook = books.stream()
                .anyMatch(book -> book.getTitle().contains("Java"));
        assertTrue(hasJavaBook);

        boolean hasEffectiveJava = books.stream()
                .anyMatch(book -> book.getTitle().equals("Effective Java"));
        assertTrue(hasEffectiveJava);
    }
}