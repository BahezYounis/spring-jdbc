package com.aga.spring_jdbc.dao.impl.AuthorTests;

import com.aga.spring_jdbc.TestDataUtil;
import com.aga.spring_jdbc.daos.impl.AuthorDaoImpl;
import com.aga.spring_jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoIntegrationTests {

    private final AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author author1 = TestDataUtil.createTestAuthor();
        Author author2 = TestDataUtil.generateTestAuthor(102L, "Bahez", 28);
        underTest.create(author1);
        underTest.create(author2);
        List<Author> result = underTest.findAll();

        assertThat(result).hasSize(2).containsExactly(author1, author2);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        author.setName("Updated Author");
        underTest.update(author.getId(), author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        underTest.delete(author.getId());
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isNotPresent();
    }
}
