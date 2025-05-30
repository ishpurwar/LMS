package com.abes.lms.exception;

public class BookAlreadyExistException extends Exception {

    public BookAlreadyExistException(String message) {
        super(message);
    }

    public BookAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
}

// Thrown if book already exists and Librarian tries to add it book again.