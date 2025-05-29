package com.abes.lms.ui;

public class Ui {
    public static void starter() {
        System.out.println("Welcome to the Library Management System");
        // Additional UI initialization code can go here
    }

    // Librarian Operations
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
                    user.getBorrowedBooks().forEach(book -> 
                        System.out.println("    - " + book.getTitle()));
                }
            });
        }
    }
}

