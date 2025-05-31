package com.abes.lms.ui;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.abes.lms.dto.BookDTO;

public class UiLowLevelTest {

    @Test
    void testParseMenuChoice_ValidCases() {
        assertEquals(1, Ui.parseMenuChoice("1"));
        assertEquals(0, Ui.parseMenuChoice("0"));
        assertEquals(42, Ui.parseMenuChoice("42"));
        assertEquals(99, Ui.parseMenuChoice("99"));
    }

    @Test
    void testParseMenuChoice_InvalidCases() {
        assertNull(Ui.parseMenuChoice("abc"));
        assertNull(Ui.parseMenuChoice(""));
        assertNull(Ui.parseMenuChoice(" "));
        assertNull(Ui.parseMenuChoice("1.5"));
    }
     @Test
    void testSortBooksById() {
        List<BookDTO> books = Arrays.asList(
            new BookDTO("AuthorC", "C", 3, 4.0),
            new BookDTO("AuthorA", "A", 1, 3.2),
            new BookDTO("AuthorB", "B", 2, 5.0)
        );
        // Suppose you refactor to: BookSortUtil.sortById(books)
        books.sort(Comparator.comparingInt(BookDTO::getId));
        assertEquals(1, books.get(0).getId());
        assertEquals(2, books.get(1).getId());
        assertEquals(3, books.get(2).getId());
    }


}