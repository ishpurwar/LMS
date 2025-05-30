package com.abes.lms.service;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.exception.InputValidationException;
import com.abes.lms.exception.UserNotFoundException;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DisplayName("UserService Test Suite")
public class UserServiceTest {

	private UserService userService;
	private static final String TEST_USER = "testuser";
	private static final String TEST_PASSWORD = "testpass123";
	private static final String TEST_BOOK_TITLE = "Java: The Complete Reference";

	@BeforeEach
	void setUp() {
		userService = new UserService();
		CollectionUtil.getAllUsers().clear();
		resetBooksToInitialState();
	}

	private void resetBooksToInitialState() {
		List<BookDTO> books = CollectionUtil.getAllBooks();
		books.forEach(book -> {
			book.setAvailable(true);
			book.setBorrowedBy(null);
		});
	}

	@Test
	@DisplayName("Should register user successfully with valid details")
	void testRegisterUser_Success() {
		assertDoesNotThrow(() -> {
			userService.registerUser(TEST_USER, TEST_PASSWORD);
		});
		assertTrue(userService.authenticateUser(TEST_USER, TEST_PASSWORD));
	}

	@Test
	@DisplayName("Should throw exception when registering user with empty name")
	void testRegisterUser_EmptyName() {
		InputValidationException exception = assertThrows(InputValidationException.class, () -> {
			userService.registerUser("", TEST_PASSWORD);
		});
		assertEquals("User name cannot be empty", exception.getMessage());
	}

	@Test
	@DisplayName("Should throw exception when registering user with short name")
	void testRegisterUser_ShortName() {
		InputValidationException exception = assertThrows(InputValidationException.class, () -> {
			userService.registerUser("ab", TEST_PASSWORD);
		});
		assertEquals("User name must be at least 3 characters long", exception.getMessage());
	}

	@Test
	@DisplayName("Should throw exception when registering user with long name")
	void testRegisterUser_LongName() {
		String longName = "a".repeat(31);
		InputValidationException exception = assertThrows(InputValidationException.class, () -> {
			userService.registerUser(longName, TEST_PASSWORD);
		});
		assertEquals("User name cannot exceed 30 characters", exception.getMessage());
	}

	@Test
	@DisplayName("Should throw exception when registering user with short password")
	void testRegisterUser_ShortPassword() {
		InputValidationException exception = assertThrows(InputValidationException.class, () -> {
			userService.registerUser(TEST_USER, "123");
		});
		assertEquals("Password must be at least 4 characters long", exception.getMessage());
	}

	@Test
	@DisplayName("Should throw exception when registering duplicate user")
	void testRegisterUser_DuplicateUser() {
		assertDoesNotThrow(() -> userService.registerUser(TEST_USER, TEST_PASSWORD));

		InputValidationException exception = assertThrows(InputValidationException.class, () -> {
			userService.registerUser(TEST_USER, "newpassword");
		});
		assertEquals("User '" + TEST_USER + "' already exists", exception.getMessage());
	}

	@Test
	@DisplayName("Should authenticate user with correct credentials")
	void testAuthenticateUser_Success() {
		assertDoesNotThrow(() -> userService.registerUser(TEST_USER, TEST_PASSWORD));
		assertTrue(userService.authenticateUser(TEST_USER, TEST_PASSWORD));
	}

	@Test
	@DisplayName("Should fail authentication with wrong password")
	void testAuthenticateUser_WrongPassword() {
		assertDoesNotThrow(() -> userService.registerUser(TEST_USER, TEST_PASSWORD));
		assertFalse(userService.authenticateUser(TEST_USER, "wrongpassword"));
	}

	@Test
	@DisplayName("Should fail authentication with non-existent user")
	void testAuthenticateUser_NonExistentUser() {
		assertFalse(userService.authenticateUser("nonexistent", TEST_PASSWORD));
	}

	@Test
	@DisplayName("Should return all books")
	void testShowAllBooks() {
		List<BookDTO> books = userService.showAllBooks();
		assertNotNull(books);
		assertFalse(books.isEmpty());
		assertEquals(4, books.size()); 
	}

	@Test
	@DisplayName("Should borrow book successfully")
	void testBorrowBook_Success() throws Exception {
		userService.registerUser(TEST_USER, TEST_PASSWORD);

		assertDoesNotThrow(() -> {
			userService.borrowBook(TEST_USER, TEST_BOOK_TITLE);
		});

		BookDTO book = CollectionUtil.findBookByTitle(TEST_BOOK_TITLE);
		assertFalse(book.isAvailable());
		assertEquals(TEST_USER, book.getBorrowedBy());
	}

	@Test
	@DisplayName("Should throw exception when borrowing non-existent book")
	void testBorrowBook_BookNotFound() throws Exception {
		userService.registerUser(TEST_USER, TEST_PASSWORD);

		BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
			userService.borrowBook(TEST_USER, "Non-existent Book");
		});
		assertTrue(exception.getMessage().contains("not found"));
	}

	@Test
	@DisplayName("Should throw exception when non-existent user tries to borrow")
	void testBorrowBook_UserNotFound() {
		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
			userService.borrowBook("nonexistent", TEST_BOOK_TITLE);
		});
		assertTrue(exception.getMessage().contains("not found"));
	}

	@Test
	@DisplayName("Should throw exception when borrowing already borrowed book")
	void testBorrowBook_AlreadyBorrowed() throws Exception {
		userService.registerUser(TEST_USER, TEST_PASSWORD);
		userService.registerUser("user2", TEST_PASSWORD);

		userService.borrowBook(TEST_USER, TEST_BOOK_TITLE);

		InputValidationException exception = assertThrows(InputValidationException.class, () -> {
			userService.borrowBook("user2", TEST_BOOK_TITLE);
		});
		assertTrue(exception.getMessage().contains("already borrowed"));
	}

	@Test
	@DisplayName("Should return book successfully")
	void testReturnBook_Success() throws Exception {
		userService.registerUser(TEST_USER, TEST_PASSWORD);
		userService.borrowBook(TEST_USER, TEST_BOOK_TITLE);

		assertDoesNotThrow(() -> {
			userService.returnBook(TEST_USER, TEST_BOOK_TITLE);
		});

		BookDTO book = CollectionUtil.findBookByTitle(TEST_BOOK_TITLE);
		assertTrue(book.isAvailable());
		assertNull(book.getBorrowedBy());
	}

	@Test
	@DisplayName("Should throw exception when returning non-borrowed book")
	void testReturnBook_NotBorrowed() throws Exception {
		userService.registerUser(TEST_USER, TEST_PASSWORD);

		InputValidationException exception = assertThrows(InputValidationException.class, () -> {
			userService.returnBook(TEST_USER, TEST_BOOK_TITLE);
		});
		assertTrue(exception.getMessage().contains("not currently borrowed"));
	}

	@Test
	@DisplayName("Should throw exception when user returns book they didn't borrow")
	void testReturnBook_NotBorrowedByUser() throws Exception {
		userService.registerUser(TEST_USER, TEST_PASSWORD);
		userService.registerUser("user2", TEST_PASSWORD);

		userService.borrowBook(TEST_USER, TEST_BOOK_TITLE);

		InputValidationException exception = assertThrows(InputValidationException.class, () -> {
			userService.returnBook("user2", TEST_BOOK_TITLE);
		});
		assertTrue(exception.getMessage().contains("haven't borrowed"));
	}

	@Test
	@DisplayName("Should edit user details successfully")
	void testEditUserDetails_Success() throws Exception {
		userService.registerUser(TEST_USER, TEST_PASSWORD);

		String newName = "newusername";
		String newPassword = "newpassword123";

		assertDoesNotThrow(() -> {
			userService.editUserDetails(TEST_USER, newName, newPassword);
		});

		assertTrue(userService.authenticateUser(newName, newPassword));
		assertFalse(userService.authenticateUser(TEST_USER, TEST_PASSWORD));
	}
	@Test
	@DisplayName("Should edit only username when password is null")
	void testEditUserDetails_OnlyUsername() throws Exception {
		userService.registerUser(TEST_USER, TEST_PASSWORD);

		String newName = "newusername";

		assertDoesNotThrow(() -> {
			userService.editUserDetails(TEST_USER, newName, null);
		});

		assertTrue(userService.authenticateUser(newName, TEST_PASSWORD));
	}

	@Test
	@DisplayName("Should edit only password when username is null")
	void testEditUserDetails_OnlyPassword() throws Exception {
		userService.registerUser(TEST_USER, TEST_PASSWORD);

		String newPassword = "newpassword123";

		assertDoesNotThrow(() -> {
			userService.editUserDetails(TEST_USER, null, newPassword);
		});

		assertTrue(userService.authenticateUser(TEST_USER, newPassword));
	}

	@Test
	@DisplayName("Should throw exception when editing to existing username")
	void testEditUserDetails_DuplicateUsername() throws Exception {
		userService.registerUser(TEST_USER, TEST_PASSWORD);
		userService.registerUser("existinguser", TEST_PASSWORD);

		InputValidationException exception = assertThrows(InputValidationException.class, () -> {
			userService.editUserDetails(TEST_USER, "existinguser", null);
		});
		assertTrue(exception.getMessage().contains("already exists"));
	}

	@Test
	@DisplayName("Should sort books by ID")
	void testSortBooksById() {
		List<BookDTO> sortedBooks = userService.sortBooksById();

		assertNotNull(sortedBooks);
		assertFalse(sortedBooks.isEmpty());

		for (int i = 1; i < sortedBooks.size(); i++) {
			assertTrue(sortedBooks.get(i - 1).getId() <= sortedBooks.get(i).getId());
		}
	}

	@Test
	@DisplayName("Should sort books by rating in descending order")
	void testSortBooksByRating() {
		List<BookDTO> sortedBooks = userService.sortBooksByRating();

		assertNotNull(sortedBooks);
		assertFalse(sortedBooks.isEmpty());

		for (int i = 1; i < sortedBooks.size(); i++) {
			assertTrue(sortedBooks.get(i - 1).getRating() >= sortedBooks.get(i).getRating());
		}
	}

	@Test
	@DisplayName("Should sort books by title alphabetically")
	void testSortBooksByTitle() {
		List<BookDTO> sortedBooks = userService.sortBooksByTitle();

		assertNotNull(sortedBooks);
		assertFalse(sortedBooks.isEmpty());

		for (int i = 1; i < sortedBooks.size(); i++) {
			assertTrue(sortedBooks.get(i - 1).getTitle().compareTo(sortedBooks.get(i).getTitle()) <= 0);
		}
	}

	@Test
	@DisplayName("Should get user details successfully")
	void testGetUserDetails_Success() throws Exception {
		userService.registerUser(TEST_USER, TEST_PASSWORD);

		UserDto user = userService.getUserDetails(TEST_USER);

		assertNotNull(user);
		assertEquals(TEST_USER, user.getName());
		assertEquals(TEST_PASSWORD, user.getPassword());
		assertNotNull(user.getBorrowedBooks());
	}

	@Test
	@DisplayName("Should throw exception when getting details of non-existent user")
	void testGetUserDetails_UserNotFound() {
		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
			userService.getUserDetails("nonexistent");
		});
		assertTrue(exception.getMessage().contains("not found"));
	}

}