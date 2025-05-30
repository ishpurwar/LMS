package com.abes.lms.dao;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DisplayName("BookDao Implementation Tests")
public class BookDaoImplTest {

    private BookDao bookDao;
    private BookDTO testBook;

    @BeforeEach
    void setUp() {
        bookDao = new BookDaoImpl();
        testBook = new BookDTO("Test Book", "Test Author", 999, 4.0);
    }

    @Test
    @DisplayName("Should return all books successfully")
    void testGetAllBooks() {
        List<BookDTO> books = bookDao.getAllBooks();
        assertNotNull(books, "Books list should not be null");
        assertFalse(books.isEmpty(), "Books list should not be empty");
        assertTrue(books.size() >= 4, "Should contain at least 4 default books");
    }

    @Test
    @DisplayName("Should add book successfully")
    void testAddBook() {
        int initialSize = bookDao.getAllBooks().size();
        bookDao.addBook(testBook);
        
        List<BookDTO> books = bookDao.getAllBooks();
        assertEquals(initialSize + 1, books.size(), "Book count should increase by 1");
        assertTrue(bookDao.isBookPresent("Test Book"), "Added book should be present");
    }
}
    