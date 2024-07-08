package com.aga.spring_jdbc.dao.impl.bookTests;

import com.aga.spring_jdbc.daos.impl.BookDaoImpl;
import com.aga.spring_jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.aga.spring_jdbc.TestDataUtil.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {
        Book book = createTestBook();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("978-1-2345-6789-0"),
                eq("Book Title"),
                eq(1L)
        );
    }

    @Test
    public void testThatFindOneBookGeneratesCorrectSql() {
        underTest.find("978-8-2345-6789-0");

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("978-8-2345-6789-0")
        );
    }

    @Test
    public void testThatFindAllBooksGeneratesCorrectSql() {
        underTest.findAll();

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateBookGeneratesCorrectSql() {
        Book book = createTestBook();
        underTest.update(book.getIsbn(), book);
        verify(jdbcTemplate).update(
                "UPDATE books SET title = ?, author_id = ? WHERE isbn = ?",
        book.getTitle(), book.getAuthorId(), book.getIsbn());
    }

    @Test
    public void testThatDeleteBookGeneratesCorrectSql() {
        Book book = createTestBook();

        underTest.delete(book.getIsbn());

        verify(jdbcTemplate).update("DELETE FROM books WHERE isbn = ?", book.getIsbn());
    }
}
