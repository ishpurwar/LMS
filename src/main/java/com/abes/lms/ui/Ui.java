package com.abes.lms.ui;

import com.abes.lms.exception.InputValidationException;
import com.abes.lms.exception.UserAlreadyExistException;
import com.abes.lms.service.LibrarianService;
import com.abes.lms.service.UserService;
import java.util.Scanner;

public class Ui {
    private Scanner scanner;
    private UserService userService;
    private LibrarianService librarianService;
    private UserUI userUI;
    private LibrarianUI librarianUI;

    public Ui() {
        this.scanner = new Scanner(System.in);
        this.userService = new UserService();
        this.librarianService = new LibrarianService();
        this.userUI = new UserUI(scanner, userService);
        this.librarianUI = new LibrarianUI(scanner, librarianService, userService);
    }

    public static void starter() {
        System.out.println("===========================================");
        System.out.println("  Welcome to the Library Management System");
        System.out.println("===========================================");

        Ui ui = new Ui();
        ui.mainMenu();
    }

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

    private void userRegistration() {
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

    private void userLogin() {
        System.out.println("\n--- USER LOGIN ---");
        System.out.print("Enter username: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userService.authenticateUser(name, password)) {
            System.out.println("Login successful! Welcome, " + name);
            userUI.setCurrentUser(name);
            userUI.userMenu();
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void librarianLogin() {
        System.out.println("\n--- LIBRARIAN LOGIN ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (librarianService.authenticateLibrarian(username, password)) {
            System.out.println("Login successful! Welcome, Librarian");
            librarianUI.librarianMenu();
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }
}