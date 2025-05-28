package com.abes.lms.service;

import com.abes.lms.dao.BookDao;
import com.abes.lms.dao.BookDaoImpl;
import com.abes.lms.dao.UserDao;
import com.abes.lms.dao.UserDaoImpl;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.exception.InputValidationException;
import com.abes.lms.util.CollectionUtil;
import com.abes.lms.util.InputValidator;
import java.util.List;

public class LibrarianService {
    private BookDao bookDao;
    private UserDao userDao;

    public LibrarianService() {
        this.bookDao = new BookDaoImpl();
        this.userDao = new UserDaoImpl();
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
