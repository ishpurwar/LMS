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
    }