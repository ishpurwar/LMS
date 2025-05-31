package com.abes.lms.dto;

import java.util.Objects;

public class BookDTO {


	@Override
	public int hashCode() {
		return Objects.hash(author, borrowedBy, id, isAvailable, rating, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookDTO other = (BookDTO) obj;
		return Objects.equals(author, other.author) && Objects.equals(borrowedBy, other.borrowedBy) && id == other.id
				&& isAvailable == other.isAvailable
				&& Double.doubleToLongBits(rating) == Double.doubleToLongBits(other.rating)
				&& Objects.equals(title, other.title);
	}

	private String title;
    private String author;
    private int id;
    private double rating;
    private boolean isAvailable;
    private String borrowedBy;

    public BookDTO() {
        this.isAvailable = true;
    }

    public BookDTO(String title, String author, int id, double rating) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.rating = rating;
        this.isAvailable = true;
        this.borrowedBy = null;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public String getBorrowedBy() { return borrowedBy; }
    public void setBorrowedBy(String borrowedBy) { this.borrowedBy = borrowedBy; }

    @Override
    public String toString() {
        return String.format("ID: %d | Title: %s | Author: %s | Rating: %.1f | Available: %s%s",
                id, title, author, rating, isAvailable ? "Yes" : "No",
                !isAvailable ? " (Borrowed by: " + borrowedBy + ")" : "");
    }
}
