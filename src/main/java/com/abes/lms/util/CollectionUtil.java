package com.abes.lms.util;

import java.util.ArrayList;
import java.util.List;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDto;

public class CollectionUtil {
    // Static data structures to store all data
    private static List<BookDTO> books = new ArrayList<>();
    private static List<UserDto> users = new ArrayList<>();
    
    // Default librarian credentials
    public static final String LIBRARIAN_USERNAME = "admin";
    public static final String LIBRARIAN_PASSWORD = "admin123";

    // Initialize with some sample data
    static {
        // Add some sample books
        books.add(new BookDTO("Java: The Complete Reference", "Herbert Schildt", 101, 4.5));
        books.add(new BookDTO("Effective Java", "Joshua Bloch", 102, 4.8));
        books.add(new BookDTO("Spring Boot in Action", "Craig Walls", 103, 4.3));
        books.add(new BookDTO("Clean Code", "Robert Martin", 104, 4.7));
    }

    // Book-related static methods
    public static List<BookDTO> getAllBooks() {
        return new ArrayList<>(books);
    }

    public static void addBook(BookDTO book) {
        books.add(book);
    }

    public static boolean removeBook(String title) {
        return books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    public static BookDTO findBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public static BookDTO findBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static boolean isBookPresent(String title) {
        return books.stream()
                .anyMatch(book -> book.getTitle().equalsIgnoreCase(title));
    }

    // User-related static methods
    public static List<UserDto> getAllUsers() {
        return new ArrayList<>(users);
    }

    //more changes

    public static void addUser(UserDto user) {
        users.add(user);
    }

    public static UserDto findUserByName(String name) {
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static boolean isUserExists(String name) {
        return users.stream()
                .anyMatch(user -> user.getName().equalsIgnoreCase(name));
    }

    public static int getNextBookId() {
        return books.stream()
                .mapToInt(BookDTO::getId)
                .max()
                .orElse(100) + 1;
    }

    
}