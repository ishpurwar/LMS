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

    
}