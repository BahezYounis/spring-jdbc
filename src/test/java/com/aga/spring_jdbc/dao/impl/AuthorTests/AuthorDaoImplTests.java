package com.aga.spring_jdbc.dao.impl.AuthorTests;

import com.aga.spring_jdbc.daos.impl.AuthorDaoImpl;
import com.aga.spring_jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.aga.spring_jdbc.TestDataUtil.*;
import static com.aga.spring_jdbc.daos.impl.AuthorDaoImpl.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;


    @Test
    public void testThatCreateAuthorGenerateCorrectSql() {
        Author author = createTestAuthor();
        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L),
                eq("John Doe"),
                eq(25)
        );
    }

    @Test
    public void testThatFindOneAuthorGenerateCorrectSql() {
        underTest.findOne(1L);

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatFindAllAuthorGenerateCorrectSql() {
        underTest.findAll();

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorRowMapper>any()
        );
    }


    @Test
    public void testThatUpdateAuthorGenerateCorrectSql() {
        Author author = createTestAuthor();

        underTest.update(author.getId(), author);

        verify(jdbcTemplate).update(
                "UPDATE authors SET name = ?, age = ? WHERE id = ?",
                author.getName(), author.getAge(), author.getId());
    }

    @Test
    public void testThatDeleteAuthorGenerateCorrectSql() {
        Author author = createTestAuthor();

        underTest.delete(author.getId());

        verify(jdbcTemplate).update("DELETE FROM authors WHERE id = ?", author.getId());
    }

}
