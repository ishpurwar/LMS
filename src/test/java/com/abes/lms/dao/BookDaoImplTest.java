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

	@Test
	@DisplayName("Should remove existing book successfully")
	void testRemoveExistingBook() throws BookNotFoundException {
		bookDao.addBook(testBook);
		assertTrue(bookDao.isBookPresent("Test Book"), "Book should be present before removal");

		boolean removed = bookDao.removeBook("Test Book");
		assertTrue(removed, "Should return true when book is removed");
		assertFalse(bookDao.isBookPresent("Test Book"), "Book should not be present after removal");
	}

	@Test
	@DisplayName("Should throw exception when removing non-existent book")
	void testRemoveNonExistentBook() {
		assertThrows(BookNotFoundException.class, () -> {
			bookDao.removeBook("Non-existent Book");
		}, "Should throw BookNotFoundException for non-existent book");
	}

	@Test
	@DisplayName("Should find book by title successfully")
	void testFindBookByTitle() throws BookNotFoundException {
		bookDao.addBook(testBook);

		BookDTO found = bookDao.findBookByTitle("Test Book");
		assertNotNull(found, "Found book should not be null");
		assertEquals("Test Book", found.getTitle(), "Book title should match");
		assertEquals("Test Author", found.getAuthor(), "Book author should match");
		assertEquals(4.0, found.getRating(), "Book rating should match");
	}

	@Test
	@DisplayName("Should throw exception when finding non-existent book by title")
	void testFindNonExistentBookByTitle() {
		assertThrows(BookNotFoundException.class, () -> {
			bookDao.findBookByTitle("Non-existent Book");
		}, "Should throw BookNotFoundException for non-existent book");
	}

	@Test
	@DisplayName("Should find book by ID successfully")
	void testFindBookById() throws BookNotFoundException {
		bookDao.addBook(testBook);

		BookDTO found = bookDao.findBookById(999);
		assertNotNull(found, "Found book should not be null");
		assertEquals(999, found.getId(), "Book ID should match");
		assertEquals("Test Book", found.getTitle(), "Book title should match");
	}

	@Test
	@DisplayName("Should throw exception when finding non-existent book by ID")
	void testFindNonExistentBookById() {
		assertThrows(BookNotFoundException.class, () -> {
			bookDao.findBookById(99999);
		}, "Should throw BookNotFoundException for non-existent book ID");
	}

	@Test
	@DisplayName("Should check book presence correctly")
	void testIsBookPresent() {
		assertTrue(bookDao.isBookPresent("Java: The Complete Reference"), "Should return true for existing book");
		assertFalse(bookDao.isBookPresent("Non-existent Book"), "Should return false for non-existing book");
		assertTrue(bookDao.isBookPresent("java: the complete reference"), "Should be case insensitive");
	}

	@Test
	@DisplayName("Should handle case insensitive search")
	void testCaseInsensitiveOperations() throws BookNotFoundException {
		bookDao.addBook(testBook);
		BookDTO found = bookDao.findBookByTitle("test book");
		assertNotNull(found, "Should find book with different case");
		assertTrue(bookDao.isBookPresent("TEST BOOK"), "Should find book with uppercase");
		assertTrue(bookDao.removeBook("TeSt BoOk"), "Should remove book with mixed case");
		assertFalse(bookDao.isBookPresent("Test Book"), "Book should be removed");
	}
}
