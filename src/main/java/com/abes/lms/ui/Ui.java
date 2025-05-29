private Scanner scanner;
    private UserService userService;
    private LibrarianService librarianService;
    private String currentUser;

    public Ui() {
        this.scanner = new Scanner(System.in);
        this.userService = new UserService();
        this.librarianService = new LibrarianService();
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
                user.getBorrowedBooks().forEach(book -> 
                    System.out.println("  - " + book.getTitle()));
            }
        } catch (UserNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
