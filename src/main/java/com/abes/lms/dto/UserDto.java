package com.abes.lms.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private String name;
    private String password;
    private List<BookDTO> borrowedBooks;

    public UserDto() {
        this.borrowedBooks = new ArrayList<>();
    }

    public UserDto(String name, String password) {
        this.name = name;
        this.password = password;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<BookDTO> getBorrowedBooks() { return borrowedBooks; }
    public void setBorrowedBooks(List<BookDTO> borrowedBooks) { this.borrowedBooks = borrowedBooks; }

    public void addBorrowedBook(BookDTO book) {
        this.borrowedBooks.add(book);
    }

    public void removeBorrowedBook(BookDTO book) {
        this.borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return String.format("User: %s | Borrowed Books: %d", name, borrowedBooks.size());
    }
}