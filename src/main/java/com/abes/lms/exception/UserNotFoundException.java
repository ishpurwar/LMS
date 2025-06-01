package com.abes.lms.exception;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message) {
		super(message);
	}
}

//Thrown when an unregistered user details are entered.
