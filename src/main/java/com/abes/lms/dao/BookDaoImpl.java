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

  
}
