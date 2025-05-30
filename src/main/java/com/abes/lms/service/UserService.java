package com.abes.lms.service;

import java.util.List;

import com.abes.lms.dao.BookDao;
import com.abes.lms.dao.BookDaoImpl;
import com.abes.lms.dao.UserDao;
import com.abes.lms.dao.UserDaoImpl;
import com.abes.lms.dto.BookDTO;
import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.UserNotFoundException;

public class UserService {
    private BookDao bookDao;
    private UserDao userDao;
    public UserService() {
        this.bookDao = new BookDaoImpl();
        this.userDao = new UserDaoImpl();
    }
    public List<BookDTO> getAllBooks() {
        return bookDao.getAllBooks();
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
