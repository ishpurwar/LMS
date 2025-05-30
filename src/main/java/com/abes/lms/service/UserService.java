package com.abes.lms.service;

import com.abes.lms.dao.BookDao;
import com.abes.lms.dao.BookDaoImpl;
import com.abes.lms.dao.UserDao;
import com.abes.lms.dao.UserDaoImpl;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.exception.InputValidationException;
import com.abes.lms.exception.UserNotFoundException;
import com.abes.lms.util.InputValidator;
import java.util.List;

public class UserService {
    private BookDao bookDao;
    private UserDao userDao;

    public UserService() {
        this.bookDao = new BookDaoImpl();
        this.userDao = new UserDaoImpl();
    }

    public void registerUser(String name, String password) throws InputValidationException {
        InputValidator.validateUserName(name);
        InputValidator.validatePassword(password);

        if (userDao.isUserExists(name)) {
            throw new InputValidationException("User '" + name + "' already exists");
        }

        UserDto user = new UserDto(name, password);
        userDao.addUser(user);
    }

    public boolean authenticateUser(String name, String password) {
        return userDao.authenticateUser(name, password);
    }

    public List<BookDTO> showAllBooks() {
        return bookDao.getAllBooks();
    }

    public void borrowBook(String userName, String bookTitle) throws BookNotFoundException, UserNotFoundException, InputValidationException {
        UserDto user = userDao.findUserByName(userName);
        BookDTO book = bookDao.findBookByTitle(bookTitle);

        if (!book.isAvailable()) {
            throw new InputValidationException("Book '" + bookTitle + "' is already borrowed");
        }

        book.setAvailable(false);
        book.setBorrowedBy(userName);
        user.addBorrowedBook(book);
    }

    public void returnBook(String userName, String bookTitle) throws BookNotFoundException, UserNotFoundException, InputValidationException {
        UserDto user = userDao.findUserByName(userName);
        BookDTO book = bookDao.findBookByTitle(bookTitle);

        if (book.isAvailable()) {
            throw new InputValidationException("Book '" + bookTitle + "' is not currently borrowed");
        }

        if (!bookTitle.equals(book.getTitle()) || !userName.equals(book.getBorrowedBy())) {
            throw new InputValidationException("You haven't borrowed this book");
        }

        book.setAvailable(true);
        book.setBorrowedBy(null);
        user.removeBorrowedBook(book);
    }

    public void editUserDetails(String oldName, String newName, String newPassword) throws UserNotFoundException, InputValidationException {
        UserDto user = userDao.findUserByName(oldName);
        
        if (newName != null && !newName.trim().isEmpty()) {
            InputValidator.validateUserName(newName);
            if (!oldName.equals(newName) && userDao.isUserExists(newName)) {
                throw new InputValidationException("User '" + newName + "' already exists");
            }
            user.setName(newName);
        }

        if (newPassword != null && !newPassword.trim().isEmpty()) {
            InputValidator.validatePassword(newPassword);
            user.setPassword(newPassword);
        }
    }

    public List<BookDTO> sortBooksById() {
        return bookDao.sortBooksById();
    }

    public List<BookDTO> sortBooksByRating() {
        return bookDao.sortBooksByRating();
    }

    public List<BookDTO> sortBooksByTitle() {
        return bookDao.sortBooksByTitle();
    }

    public UserDto getUserDetails(String userName) throws UserNotFoundException {
        return userDao.findUserByName(userName);
    }
}