package com.abes.lms.ui;

import java.util.List;
import java.util.Scanner;

/**
 * UI class for Library Management System (LMS)
 * Handles user interaction through the console, including
 * user registration/login, librarian login, and respective menus.
 */

import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.BookAlreadyBorrowException;
import com.abes.lms.exception.BookAlreadyExistException;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.exception.InputValidationException;
import com.abes.lms.exception.UserAlreadyExistException;
import com.abes.lms.exception.UserNotFoundException;
import com.abes.lms.service.LibrarianService;
import com.abes.lms.service.UserService;

/** Constructor initializes scanner and service layers. */

public class Ui {
	private Scanner scanner;
	private UserService userService;
	private LibrarianService librarianService;
	private String currentUser;

	public Ui() {
		this.scanner = new Scanner(System.in);
		this.userService = new UserService();
		this.librarianService = new LibrarianService();
	}

	/** 
	 * Entry point method to start the LMS application.
	 */

	public static void starter() {
		System.out.println("===========================================");
		System.out.println("  Welcome to the Library Management System");
		System.out.println("===========================================");

		Ui ui = new Ui();
		ui.mainMenu();
	}

	/**
	 * Displays the main menu and routes user to registration/login flows.
	 */

	private void mainMenu() {
		while (true) {
			System.out.println("\n--- MAIN MENU ---");
			System.out.println("1. User Registration");
			System.out.println("2. User Login");
			System.out.println("3. Librarian Login");
			System.out.println("4. Exit");
			System.out.print("Choose an option: ");

			try {
				int choice = Integer.parseInt(scanner.nextLine());
				switch (choice) {
				case 1:
					userRegistration();
					break;
				case 2:
					userLogin();
					break;
				case 3:
					librarianLogin();
					break;
				case 4:
					System.out.println("Thank you for using Library Management System!");
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number.");
			}
		}
	}

	/**
	 * Handles user registration with input validation and error handling.
	 */

	void userRegistration() {
		System.out.println("\n--- USER REGISTRATION ---");
		System.out.print("Enter username: ");
		String name = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		try {
			userService.registerUser(name, password);
			System.out.println("Registration successful!");
		} catch (InputValidationException e) {
			System.out.println("Registration failed: " + e.getMessage());
		}
		catch (UserAlreadyExistException e){
			System.out.println("Registration failed: " + e.getMessage());
		}
		catch (Exception e) {
			System.out.println("An unexpected error occurred: " + e.getMessage());
		}
	}

	/**
	 * Handles user login, sets current user on success.
	 */

	private void userLogin() {
		System.out.println("\n--- USER LOGIN ---");
		System.out.print("Enter username: ");
		String name = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		if (userService.authenticateUser(name, password)) {
			System.out.println("Login successful! Welcome, " + name);
			currentUser = name;
			userMenu();
		} else {
			System.out.println("Invalid credentials. Please try again.");
		}
	}

	/**
	 * Handles librarian login and navigates to librarian menu on success.
	 */

	private void librarianLogin() {
		System.out.println("\n--- LIBRARIAN LOGIN ---");
		System.out.print("Enter username: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		if (librarianService.authenticateLibrarian(username, password)) {
			System.out.println("Login successful! Welcome, Librarian");
			librarianMenu();
		} else {
			System.out.println("Invalid credentials. Please try again.");
		}
	}

	/**
	 * Displays user-specific menu and routes to user operations.
	 */

	private void userMenu() {
		while (true) {
			System.out.println("\n---- USER MENU ----");
			System.out.println("1. Show All Books");
			System.out.println("2. Borrow Book");
			System.out.println("3. Return Book");
			System.out.println("4. Edit Profile");
			System.out.println("5. Sort Books by ID");
			System.out.println("6. Sort Books by Rating");
			System.out.println("7. Sort Books by Title");
			System.out.println("8. View My Profile");
			System.out.println("9. Logout");
			System.out.print("Choose an option: ");

			try {
				int choice = Integer.parseInt(scanner.nextLine());
				switch (choice) {
				case 1:
					showAllBooks();
					break;
				case 2:
					borrowBook();
					break;
				case 3:
					returnBook();
					break;
				case 4:
					editProfile();
					break;
				case 5:
					sortBooksById();
					break;
				case 6:
					sortBooksByRating();
					break;
				case 7:
					sortBooksByTitle();
					break;
				case 8:
					viewProfile();
					break;
				case 9:
					currentUser = null;
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number.");
			}
		}
	}
	public static Integer parseMenuChoice(String input) {
    try {
        return Integer.parseInt(input.trim());
    } catch (NumberFormatException e) {
        return null;
    }
}

 	/**
	 * Displays librarian-specific menu and routes to admin operations.
	 */

	private void librarianMenu() {
		while (true) {
			System.out.println("\n--- LIBRARIAN MENU ---");
			System.out.println("1. Add Book");
			System.out.println("2. Remove Book");
			System.out.println("3. Check if Book Present");
			System.out.println("4. Show All Books");
			System.out.println("5. Show All Users");
			System.out.println("6. Logout");
			System.out.print("Choose an option: ");

			try {
				int choice = Integer.parseInt(scanner.nextLine());
				switch (choice) {
				case 1:
					addBook();
					break;
				case 2:
					removeBook();
					break;
				case 3:
					checkBookPresent();
					break;
				case 4:
					showAllBooks();
					break;
				case 5:
					showAllUsers();
					break;
				case 6:
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number.");
			}
		}
	}

	// User Operations

	/** Displays a list of all available books. */
	private void showAllBooks() {
		List<BookDTO> books = userService.showAllBooks();
		System.out.println("\n--- ALL BOOKS ---");
		if (books.isEmpty()) {
			System.out.println("No books available.");
		} else {
			books.forEach(System.out::println);
		}
	}

	/** Allows the current user to borrow a book by title. */
	private void borrowBook() {
		System.out.print("Enter book title to borrow: ");
		String title = scanner.nextLine();

		try {
			userService.borrowBook(currentUser, title);
			System.out.println("Book borrowed successfully!");
		} catch (BookNotFoundException | UserNotFoundException | InputValidationException | BookAlreadyBorrowException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/** Allows the current user to return a borrowed book. */
	private void returnBook() {
		System.out.print("Enter book title to return: ");
		String title = scanner.nextLine();

		try {
			userService.returnBook(currentUser, title);
			System.out.println("Book returned successfully!");
		} catch (BookNotFoundException | UserNotFoundException | InputValidationException | BookAlreadyExistException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/** Lets the current user update their profile. */
	private void editProfile() {
		System.out.println("--- EDIT PROFILE ---");
		System.out.print("Enter new username (press Enter to skip): ");
		String newName = scanner.nextLine();
		System.out.print("Enter new password (press Enter to skip): ");
		String newPassword = scanner.nextLine();

		try {
			userService.editUserDetails(currentUser, newName.isEmpty() ? null : newName,
					newPassword.isEmpty() ? null : newPassword);
			if (!newName.isEmpty()) {
				currentUser = newName;
			}
			System.out.println("Profile updated successfully!");
		} catch (UserNotFoundException | InputValidationException | UserAlreadyExistException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/** Sorts and displays books by ID. */
	private void sortBooksById() {
		List<BookDTO> books = userService.sortBooksById();
		System.out.println("\n--- BOOKS SORTED BY ID ---");
		books.forEach(System.out::println);
	}

	/** Sorts and displays books by rating. */
	private void sortBooksByRating() {
		List<BookDTO> books = userService.sortBooksByRating();
		System.out.println("\n--- BOOKS SORTED BY RATING ---");
		books.forEach(System.out::println);
	}

	/** Sorts and displays books by title. */
	private void sortBooksByTitle() {
		List<BookDTO> books = userService.sortBooksByTitle();
		System.out.println("\n--- BOOKS SORTED BY TITLE ---");
		books.forEach(System.out::println);
	}

	/** Displays profile info of the current user. */
	private void viewProfile() {
		try {
			UserDto user = userService.getUserDetails(currentUser);
			System.out.println("\n--- MY PROFILE ---");
			System.out.println("Username: " + user.getName());
			System.out.println("Borrowed Books: " + user.getBorrowedBooks().size());
			if(user == null) {
				System.out.println("User not found.");
				return;
			}
			if (!user.getBorrowedBooks().isEmpty()) {
				System.out.println("Books borrowed:");
				user.getBorrowedBooks().forEach(book -> System.out.println("  - " + book.getTitle()));
			}
		} catch (UserNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Librarian Operations

	/** Adds a new book to the library collection. */
	private void addBook() {
		System.out.println("--- ADD BOOK ---");
		System.out.print("Enter book title: ");
		String title = scanner.nextLine();
		System.out.print("Enter author: ");
		String author = scanner.nextLine();
		System.out.print("Enter rating (0.0-5.0): ");

		try {
			double rating = Double.parseDouble(scanner.nextLine());
			librarianService.addBook(title, author, rating);
			System.out.println("Book added successfully!");
		} catch (NumberFormatException e) {
			System.out.println("Invalid rating format. Please enter a decimal number.");
		} catch (InputValidationException e) {
			System.out.println("Error: " + e.getMessage());
		}
		catch (BookAlreadyExistException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("An unexpected error occurred: " + e.getMessage());
		}
	}

	/** Removes a book from the library by title. */
	private void removeBook() {
		System.out.print("Enter book title to remove: ");
		String title = scanner.nextLine();

		try {
			librarianService.removeBook(title);
			System.out.println("Book removed successfully!");
		} catch (BookNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/** Checks if a book is present in the library. */
	private void checkBookPresent() {
		System.out.print("Enter book title to check: ");
		String title = scanner.nextLine();

		if (librarianService.isBookPresent(title)) {
			System.out.println("Book '" + title + "' is present in the library.");
		} else {
			System.out.println("Book '" + title + "' is not found in the library.");
		}
	}

	/** Displays all registered users along with borrowed books. */
	private void showAllUsers() {
		List<UserDto> users = librarianService.showAllUsers();
		System.out.println("\n--- ALL USERS ---");
		if (users.isEmpty()) {
			System.out.println("No users registered.");
		} else {
			users.forEach(user -> {
				System.out.println(user);
				if (!user.getBorrowedBooks().isEmpty()) {
					System.out.println("  Borrowed books:");
					user.getBorrowedBooks().forEach(book -> System.out.println("    - " + book.getTitle()));
				}
			});
		}
	}

	
}
