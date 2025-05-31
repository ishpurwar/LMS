package com.abes.lms.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a user in the library management system.
 * This class holds user credentials and tracks the list of books borrowed by the user.
 */
public class UserDto {

    private String name;
    private String password;
    private List<BookDTO> borrowedBooks;

    /**
     * Default constructor initializing an empty list of borrowed books.
     */
    public UserDto() {
        this.borrowedBooks = new ArrayList<>();
    }

    /**
     * Parameterized constructor to create a user with specified name and password.
     *
     * @param name     Name of the user
     * @param password Password of the user
     */
    public UserDto(String name, String password) {
        this.name = name;
        this.password = password;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and Setters

    /** @return the name of the user */
    public String getName() { return name; }

    /** @param name sets the name of the user */
    public void setName(String name) { this.name = name; }

    /** @return the password of the user */
    public String getPassword() { return password; }

    /** @param password sets the password of the user */
    public void setPassword(String password) { this.password = password; }

    /** @return the list of books borrowed by the user */
    public List<BookDTO> getBorrowedBooks() { return borrowedBooks; }

    /** @param borrowedBooks sets the list of borrowed books */
    public void setBorrowedBooks(List<BookDTO> borrowedBooks) { this.borrowedBooks = borrowedBooks; }

    /**
     * Adds a book to the list of borrowed books.
     *
     * @param book the BookDTO to be added
     */
    public void addBorrowedBook(BookDTO book) {
        this.borrowedBooks.add(book);
    }

    /**
     * Removes a book from the list of borrowed books.
     *
     * @param book the BookDTO to be removed
     */
    public void removeBorrowedBook(BookDTO book) {
        this.borrowedBooks.remove(book);
    }

    /**
     * Generates a human-readable string representation of the user and borrowed book count.
     *
     * @return a formatted string with user details
     */
    @Override
    public String toString() {
        return String.format("User: %s | Borrowed Books: %d", name, borrowedBooks.size());
    }

    /**
     * Compares this UserDto with another object for equality based on fields.
     *
     * @param obj the object to compare with
     * @return true if name, password, and borrowed books are equal; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UserDto other = (UserDto) obj;
        return Objects.equals(name, other.name) &&
               Objects.equals(password, other.password) &&
               Objects.equals(borrowedBooks, other.borrowedBooks);
    }

    /**
     * Generates a hash code for the UserDto based on its fields.
     *
     * @return hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, password, borrowedBooks);
    }
}
