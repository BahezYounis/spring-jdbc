package com.aga.spring_jdbc.daos.impl;

import com.aga.spring_jdbc.daos.AuthorDao;
import com.aga.spring_jdbc.domain.Author;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Author> getAuthorByBookIsbn(String isbn) {
        final SqlRowSet result = jdbcTemplate.queryForRowSet(
                "select a.id as id, a.name as name, a.age as age "+
                        "from authors a JOIN books b ON b.author_id = a.id " +
                        "where b.isbn = ?",
                isbn);

        if (result.next()){
            return Optional.of(
                    Author.builder()
                            .id(result.getLong("id"))
                            .name(result.getString("name"))
                            .age(result.getInt("age"))
                            .build()
            );
        }
        return Optional.empty();
    }

    @Override
    public void create(Author author) {
        try {
            jdbcTemplate.update(
                    "INSERT INTO authors (id, name, age) VALUES (?, ?, ?)",
                    author.getId(), author.getName(), author.getAge()
            );
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Author> findOne(long authorId) {
        List<Author> results = jdbcTemplate.query(
                "SELECT id, name, age FROM authors WHERE id = ? LIMIT 1",
                new AuthorRowMapper(), authorId
        );
        return results.stream().findFirst();
    }

    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query("SELECT id, name, age FROM authors",
                new AuthorRowMapper()
        );
    }

    @Override
    public void update(Long id, Author author) {
        try {
            jdbcTemplate.update(
                    "UPDATE authors SET name = ?, age = ? WHERE id = ?",
                    author.getName(),
                    author.getAge(),
                    id
            );
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try {
            jdbcTemplate.update("DELETE FROM authors WHERE id = ?", id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
}
