package com.abes.lms.service;

import com.abes.lms.dao.BookDao;
import com.abes.lms.dao.BookDaoImpl;
import com.abes.lms.dao.UserDao;
import com.abes.lms.dao.UserDaoImpl;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.BookAlreadyExistException;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.exception.InputValidationException;
import com.abes.lms.util.CollectionUtil;
import com.abes.lms.util.InputValidator;
import java.util.List;

/**
 * Service layer for librarian-related operations.
 * Provides methods to authenticate librarian, manage books, and view users.
 */

//Constructor initializes DAO implementations for book and user data.
public class LibrarianService {
    private BookDao bookDao;
    private UserDao userDao;

    public LibrarianService() {
        this.bookDao = new BookDaoImpl();
        this.userDao = new UserDaoImpl();
    }

    public boolean authenticateLibrarian(String username, String password) {
        return CollectionUtil.LIBRARIAN_USERNAME.equals(username) &&
                CollectionUtil.LIBRARIAN_PASSWORD.equals(password);
    }

    public void addBook(String title, String author, double rating) throws InputValidationException,
            BookAlreadyExistException {
        InputValidator.validateBookTitle(title);
        InputValidator.validateAuthor(author);
        InputValidator.validateRating(rating);

        if (bookDao.isBookPresent(title)) {
            throw new BookAlreadyExistException("Book with title '" + title + "' already exists");
        }

        int id = CollectionUtil.getNextBookId();
        BookDTO book = new BookDTO(title, author, id, rating);
        bookDao.addBook(book);
    }

    public boolean isBookPresent(String title) {
        return bookDao.isBookPresent(title);
    }

    public List<UserDto> showAllUsers() {
        return userDao.getAllUsers();
    }

    public List<BookDTO> showAllBooks() {
        return bookDao.getAllBooks();
    }

    public void removeBook(String title) throws BookNotFoundException {
        bookDao.removeBook(title);
    }
}