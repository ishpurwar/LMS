package com.abes.lms.dao;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.util.CollectionUtil;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookDaoImpl implements BookDao {

    @Override
    public List<BookDTO> getAllBooks() {
        return CollectionUtil.getAllBooks();
    }

    @Override
    public void addBook(BookDTO book) {
        CollectionUtil.addBook(book);
    }

    @Override
    public boolean removeBook(String title) throws BookNotFoundException {
        if (!CollectionUtil.isBookPresent(title)) {
            throw new BookNotFoundException("Book '" + title + "' not found");
        }
        return CollectionUtil.removeBook(title);
    }

    @Override
    public BookDTO findBookByTitle(String title) throws BookNotFoundException {
        BookDTO book = CollectionUtil.findBookByTitle(title);
        if (book == null) {
            throw new BookNotFoundException("Book '" + title + "' not found");
        }
        return book;
    }

    @Override
    public BookDTO findBookById(int id) throws BookNotFoundException {
        BookDTO book = CollectionUtil.findBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        return book;
    }

    @Override
    public boolean isBookPresent(String title) {
        return CollectionUtil.isBookPresent(title);
    }

    @Override
    public List<BookDTO> sortBooksByRating() {
        return getAllBooks().stream()
                .sorted(Comparator.comparing(BookDTO::getRating).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> sortBooksById() {
        return getAllBooks().stream()
                .sorted(Comparator.comparing(BookDTO::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> sortBooksByTitle() {
        return getAllBooks().stream()
                .sorted(Comparator.comparing(BookDTO::getTitle))
                .collect(Collectors.toList());
    }
}
