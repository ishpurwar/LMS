package com.abes.lms.ui;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.BookAlreadyExistException;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.exception.InputValidationException;
import com.abes.lms.service.LibrarianService;
import com.abes.lms.service.UserService;
import java.util.List;
import java.util.Scanner;

public class LibrarianUI {
    private Scanner scanner;
    private LibrarianService librarianService;
    private UserService userService;

    public LibrarianUI(Scanner scanner, LibrarianService librarianService, UserService userService) {
        this.scanner = scanner;
        this.librarianService = librarianService;
        this.userService = userService;
    }

    public void librarianMenu() {
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

    private void checkBookPresent() {
        System.out.print("Enter book title to check: ");
        String title = scanner.nextLine();

        if (librarianService.isBookPresent(title)) {
            System.out.println("Book '" + title + "' is present in the library.");
        } else {
            System.out.println("Book '" + title + "' is not found in the library.");
        }
    }

    private void showAllBooks() {
        List<BookDTO> books = userService.showAllBooks();
        System.out.println("\n--- ALL BOOKS ---");
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(System.out::println);
        }
    }

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