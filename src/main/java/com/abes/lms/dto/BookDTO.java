package com.abes.lms.dto;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a book in the library system.
 */
public class BookDTO {

    private String title;
    private String author;
    private int id;
    private double rating;
    private boolean isAvailable;
    private String borrowedBy;

    /**
     * Default constructor initializes the book as available.
     */
    public BookDTO() {
        this.isAvailable = true;
    }

    /**
     * Parameterized constructor for creating a book with specified values.
     */
    public BookDTO(String title, String author, int id, double rating) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.rating = rating;
        this.isAvailable = true;
        this.borrowedBy = null;
    }

    // Getters and Setters

    /* the title of the book */
    public String getTitle() { return title; }

    /* title sets the title of the book */
    public void setTitle(String title) { this.title = title; }

    /* the author of the book */
    public String getAuthor() { return author; }

    /* author sets the author of the book */
    public void setAuthor(String author) { this.author = author; }

    /* the ID of the book */
    public int getId() { return id; }

    /* id sets the ID of the book */
    public void setId(int id) { this.id = id; }

    /* the rating of the book */
    public double getRating() { return rating; }

    /* rating sets the rating of the book */
    public void setRating(double rating) { this.rating = rating; }

    /*true if the book is available; false otherwise */
    public boolean isAvailable() { return isAvailable; }

    /* available sets the availability status of the book */
    public void setAvailable(boolean available) { isAvailable = available; }

    /* the name of the user who borrowed the book (if any) */
    public String getBorrowedBy() { return borrowedBy; }

    /* sets the name of the user who borrowed the book */
    public void setBorrowedBy(String borrowedBy) { this.borrowedBy = borrowedBy; }

    /**
     * Generates a human-readable string representation of the book.
     */
    @Override
    public String toString() {
        return String.format("ID: %d | Title: %s | Author: %s | Rating: %.1f | Available: %s%s",
                id, title, author, rating, isAvailable ? "Yes" : "No",
                !isAvailable ? " (Borrowed by: " + borrowedBy + ")" : "");
    }

    /**
     * Compares this BookDTO with another object for equality based on field values.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        BookDTO other = (BookDTO) obj;
        return id == other.id &&
                isAvailable == other.isAvailable &&
                Double.doubleToLongBits(rating) == Double.doubleToLongBits(other.rating) &&
                Objects.equals(title, other.title) &&
                Objects.equals(author, other.author) &&
                Objects.equals(borrowedBy, other.borrowedBy);
    }

    /**
     * Generates a hash code for the BookDTO based on its fields.
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, author, id, rating, isAvailable, borrowedBy);
    }
}
