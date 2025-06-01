package com.abes.lms.exception;

public class BookAlreadyBorrowException extends Exception {

    public BookAlreadyBorrowException(String message) {
        super(message);
    }
}

//Thrown when a book is already borrowed by some user.
