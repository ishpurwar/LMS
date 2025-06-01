package com.abes.lms.dao;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.abes.lms.dto.BookDTO;
import com.abes.lms.exception.BookNotFoundException;
import com.abes.lms.util.CollectionUtil;

/**
 * Implementation of the BookDao interface to manage book operations.
 * Uses CollectionUtil for storing and retrieving book data.
 */
public class BookDaoImpl implements BookDao {

    /**
     * Retrieves the list of all books.
     *
     */
    @Override
    public List<BookDTO> getAllBooks() {
        return CollectionUtil.getAllBooks();
    }

    /**
     * Adds a new book to the collection.
     *
     */
    @Override
    public void addBook(BookDTO book) {
        CollectionUtil.addBook(book);
    }

    /**
     * Removes a book from the collection by its title.
     *
     */
    @Override
    public boolean removeBook(String title) throws BookNotFoundException {
        if (!CollectionUtil.isBookPresent(title)) {
            throw new BookNotFoundException("Book '" + title + "' not found");
        }
        return CollectionUtil.removeBook(title);
    }

    /**
     * Finds and returns a book by its title.
     *
     */
    @Override
    public BookDTO findBookByTitle(String title) throws BookNotFoundException {
        BookDTO book = CollectionUtil.findBookByTitle(title);
        if (book == null) {
            throw new BookNotFoundException("Book '" + title + "' not found");
        }
        return book;
    }

    /**
     * Finds and returns a book by its ID.
     *
     */
    @Override
    public BookDTO findBookById(int id) throws BookNotFoundException {
        BookDTO book = CollectionUtil.findBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        return book;
    }

    /**
     * Checks whether a book with the given title exists.
    **/
    @Override
    public boolean isBookPresent(String title) {
        return CollectionUtil.isBookPresent(title);
    }

    /*
     * Returns a list of books sorted by rating in descending order.
     */
    @Override
    public List<BookDTO> sortBooksByRating() {
        return getAllBooks().stream()
                .sorted(Comparator.comparing(BookDTO::getRating).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of books sorted by ID in ascending order.
     *
     */
    @Override
    public List<BookDTO> sortBooksById() {
        return getAllBooks().stream()
                .sorted(Comparator.comparing(BookDTO::getId))
                .collect(Collectors.toList());
    }

    /*
     * Returns a list of books sorted by title alphabetically.
     */
    @Override
    public List<BookDTO> sortBooksByTitle() {
        return getAllBooks().stream()
                .sorted(Comparator.comparing(BookDTO::getTitle))
                .collect(Collectors.toList());
    }
    @Override
    public boolean isBookAvailable(String title) {
        try {
            BookDTO book = findBookByTitle(title);
            return book.isAvailable();
        } catch (BookNotFoundException e) {
            return false;
        }
    }
}
