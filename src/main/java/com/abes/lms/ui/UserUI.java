package com.abes.lms.ui;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.BookAlreadyBorrowException;
import com.abes.lms.exception.BookAlreadyExistException;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.exception.InputValidationException;
import com.abes.lms.exception.UserAlreadyExistException;
import com.abes.lms.exception.UserNotFoundException;
import com.abes.lms.service.UserService;
import java.util.List;
import java.util.Scanner;

public class UserUI {
    private Scanner scanner;
    private UserService userService;
    private String currentUser;

    public UserUI(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void userMenu() {
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

    private void showAllBooks() {
        List<BookDTO> books = userService.showAllBooks();
        System.out.println("\n--- ALL BOOKS ---");
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(System.out::println);
        }
    }

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

    private void sortBooksById() {
        List<BookDTO> books = userService.sortBooksById();
        System.out.println("\n--- BOOKS SORTED BY ID ---");
        books.forEach(System.out::println);
    }

    private void sortBooksByRating() {
        List<BookDTO> books = userService.sortBooksByRating();
        System.out.println("\n--- BOOKS SORTED BY RATING ---");
        books.forEach(System.out::println);
    }

    private void sortBooksByTitle() {
        List<BookDTO> books = userService.sortBooksByTitle();
        System.out.println("\n--- BOOKS SORTED BY TITLE ---");
        books.forEach(System.out::println);
    }

    private void viewProfile() {
        try {
            UserDto user = userService.getUserDetails(currentUser);
            System.out.println("\n--- MY PROFILE ---");
            System.out.println("Username: " + user.getName());
            System.out.println("Borrowed Books: " + user.getBorrowedBooks().size());
            if (!user.getBorrowedBooks().isEmpty()) {
                System.out.println("Books borrowed:");
                user.getBorrowedBooks().forEach(book -> System.out.println("  - " + book.getTitle()));
            }
        } catch (UserNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String getCurrentUser() {
        return currentUser;
    }
}