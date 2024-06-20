package com.aga.spring_jdbc.daos.impl;

import com.aga.spring_jdbc.daos.BookDao;
import com.aga.spring_jdbc.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

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
}

