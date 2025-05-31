package com.abes.lms.exception;

public class BookAlreadyBorrowException extends Exception {

    public BookAlreadyBorrowException(String message) {
        super(message);
    }

    public BookAlreadyBorrowException(String message, Throwable cause) {
        super(message, cause);
    }

}
