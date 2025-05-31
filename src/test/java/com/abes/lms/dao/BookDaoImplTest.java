package com.abes.lms.dao;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.exception.BookNotFoundException;
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
		assertNotNull(books);
		assertFalse(books.isEmpty());
		assertTrue(books.size() >= 4);
	}

	@Test
	@DisplayName("Should add book successfully")
	void testAddBook() {
		int initialSize = bookDao.getAllBooks().size();
		bookDao.addBook(testBook);
		assertEquals(initialSize + 1, bookDao.getAllBooks().size());
		assertTrue(bookDao.isBookPresent("Test Book"));
	}

	@Test
	@DisplayName("Should remove existing book successfully")
	void testRemoveExistingBook() throws BookNotFoundException {
		bookDao.addBook(testBook);
		assertTrue(bookDao.isBookPresent("Test Book"));
		assertTrue(bookDao.removeBook("Test Book"));
		assertFalse(bookDao.isBookPresent("Test Book"));
	}

	@Test
	@DisplayName("Should find book by title successfully")
	void testFindBookByTitle() throws BookNotFoundException {
		bookDao.addBook(testBook);
		BookDTO found = bookDao.findBookByTitle("Test Book");
		assertNotNull(found);
		assertEquals("Test Book", found.getTitle());
		assertEquals("Test Author", found.getAuthor());
	}

	@Test
	@DisplayName("Should find book by ID successfully")
	void testFindBookById() throws BookNotFoundException {
		bookDao.addBook(testBook);
		BookDTO found = bookDao.findBookById(999);
		assertNotNull(found);
		assertEquals(999, found.getId());
		assertEquals("Test Book", found.getTitle());
	}

	@Test
	@DisplayName("Should check book presence correctly")
	void testIsBookPresent() {
		assertTrue(bookDao.isBookPresent("Java: The Complete Reference"));
		assertFalse(bookDao.isBookPresent("Non-existent Book"));
		assertTrue(bookDao.isBookPresent("java: the complete reference"));
	}

	@Test
	@DisplayName("Should sort books by rating in descending order")
	void testSortBooksByRating() {
		List<BookDTO> sortedBooks = bookDao.sortBooksByRating();
		assertNotNull(sortedBooks);
		assertFalse(sortedBooks.isEmpty());
		for (int i = 0; i < sortedBooks.size() - 1; i++) {
			assertTrue(sortedBooks.get(i).getRating() >= sortedBooks.get(i + 1).getRating());
		}
	}

	@Test
	@DisplayName("Should sort books by ID in ascending order")
	void testSortBooksById() {
		List<BookDTO> sortedBooks = bookDao.sortBooksById();
		assertNotNull(sortedBooks);
		assertFalse(sortedBooks.isEmpty());
		for (int i = 0; i < sortedBooks.size() - 1; i++) {
			assertTrue(sortedBooks.get(i).getId() <= sortedBooks.get(i + 1).getId());
		}
	}

	@Test
	@DisplayName("Should sort books by title alphabetically")
	void testSortBooksByTitle() {
		List<BookDTO> sortedBooks = bookDao.sortBooksByTitle();
		assertNotNull(sortedBooks);
		assertFalse(sortedBooks.isEmpty());
		for (int i = 0; i < sortedBooks.size() - 1; i++) {
			assertTrue(sortedBooks.get(i).getTitle().compareTo(sortedBooks.get(i + 1).getTitle()) <= 0);
		}
	}

	@Test
	@DisplayName("Should handle case insensitive operations")
	void testCaseInsensitiveOperations() throws BookNotFoundException {
		bookDao.addBook(testBook);
		assertNotNull(bookDao.findBookByTitle("test book"));
		assertTrue(bookDao.isBookPresent("TEST BOOK"));
		assertTrue(bookDao.removeBook("TeSt BoOk"));
		assertFalse(bookDao.isBookPresent("Test Book"));
	}

}