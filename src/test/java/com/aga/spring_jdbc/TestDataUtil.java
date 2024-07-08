package com.aga.spring_jdbc;

import com.aga.spring_jdbc.domain.Author;
import com.aga.spring_jdbc.domain.Book;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static Author createTestAuthor() {
        return generateTestAuthor(1L, "John Doe", 25);
    }

    public static Author generateTestAuthor(Long id, String name, int age) {
        return Author.builder()
                .id(id)
                .name(name)
                .age(age)
                .build();
    }

    public static Book createTestBook() {
        return generateTestBook("978-8-2345-6789-0", "Book title", 1L);
    }

    public static Book generateTestBook(String isbn, String title, Long authorId) {
        return Book.builder()
                .isbn(isbn)
                .title(title)
                .authorId(authorId)
                .build();
    }
}
