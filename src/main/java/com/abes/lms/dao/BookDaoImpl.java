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
     * @return List of BookDTO objects
     */
    @Override
    public List<BookDTO> getAllBooks() {
        return CollectionUtil.getAllBooks();
    }

    /**
     * Adds a new book to the collection.
     *
     * @param book The BookDTO object to add
     */
    @Override
    public void addBook(BookDTO book) {
        CollectionUtil.addBook(book);
    }

    /**
     * Removes a book from the collection by its title.
     *
     * @param title The title of the book to remove
     * @return true if the book was removed successfully
     * @throws BookNotFoundException if the book is not found
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
     * @param title The title of the book
     * @return The matching BookDTO object
     * @throws BookNotFoundException if the book is not found
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
     * @param id The ID of the book
     * @return The matching BookDTO object
     * @throws BookNotFoundException if the book is not found
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

    /**
     * Returns a list of books sorted by rating in descending order.
     *
     * @return List of BookDTO objects sorted by rating
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
     * @return List of BookDTO objects sorted by ID
     */
    @Override
    public List<BookDTO> sortBooksById() {
        return getAllBooks().stream()
                .sorted(Comparator.comparing(BookDTO::getId))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of books sorted by title alphabetically.
     *
     * @return List of BookDTO objects sorted by title
     */
    @Override
    public List<BookDTO> sortBooksByTitle() {
        return getAllBooks().stream()
                .sorted(Comparator.comparing(BookDTO::getTitle))
                .collect(Collectors.toList());
    }
}
