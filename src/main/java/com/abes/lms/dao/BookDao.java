package com.abes.lms.dao;


import com.abes.lms.dto.BookDTO;
import com.abes.lms.exception.BookNotFoundException;
import java.util.List;

public interface BookDao {
    List<BookDTO> getAllBooks();
    void addBook(BookDTO book);
    boolean removeBook(String title) throws BookNotFoundException;
    BookDTO findBookByTitle(String title) throws BookNotFoundException;
    BookDTO findBookById(int id) throws BookNotFoundException;
    boolean isBookPresent(String title);
    List<BookDTO> sortBooksByRating();
    List<BookDTO> sortBooksById();
    List<BookDTO> sortBooksByTitle();
}