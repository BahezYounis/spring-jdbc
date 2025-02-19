package com.aga.spring_jdbc.daos.impl;

import com.aga.spring_jdbc.daos.BookDao;
import com.aga.spring_jdbc.domain.Author;
import com.aga.spring_jdbc.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> listBooks() {
        return jdbcTemplate.query("select isbn, title from books", (ResultSet rs, int rowNum) -> {
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .build();
        });
    }

    @Override
    public void create(Book book) {
        jdbcTemplate.update(
                "INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)",
                book.getIsbn(), book.getTitle(), book.getAuthorId()
        );
    }

    @Override
    public Optional<Book> find(String isbn) {
        List<Book> results =  jdbcTemplate.query("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1",
                new BookRowMapper(),
                isbn
        );
        return results.stream().findFirst();
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT isbn, title, author_id FROM books", new BookRowMapper());
    }

    @Override
    public void update(String isbn, Book book) {
        jdbcTemplate.update(
                "UPDATE books SET title = ?, author_id = ? WHERE isbn = ?",
                book.getTitle(), book.getAuthorId(), isbn
        );
    }

    @Override
    public void delete(String isbn) {
        try {
            jdbcTemplate.update( "DELETE FROM books WHERE isbn = ?", isbn);
        }catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    public static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("author_id"))
                    .build();
        }
    }
}

