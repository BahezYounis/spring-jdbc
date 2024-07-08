package com.aga.spring_jdbc.dao.impl.bookTests;

import com.aga.spring_jdbc.TestDataUtil;
import com.aga.spring_jdbc.daos.impl.AuthorDaoImpl;
import com.aga.spring_jdbc.daos.impl.BookDaoImpl;
import com.aga.spring_jdbc.domain.Author;
import com.aga.spring_jdbc.domain.Book;
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
public class BookDaoIntegrationTests {

    private final AuthorDaoImpl authorDao;

    private final BookDaoImpl underTest;

    @Autowired
    public BookDaoIntegrationTests(BookDaoImpl underTest, AuthorDaoImpl authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);
        Book book = TestDataUtil.createTestBook();
        book.setAuthorId(author.getId());
        underTest.create(book);
        Optional<Book> results = underTest.find(book.getIsbn());
        assertThat(results.isPresent()).isTrue();
        assertThat(results.get()).isEqualTo(book);
    }

    @Test
    public void testThatBooksCanBeCreatedAndRecalled() {
        Author author1 = TestDataUtil.createTestAuthor();
        authorDao.create(author1);
        Book book1 = TestDataUtil.createTestBook();
        book1.setAuthorId(author1.getId());

        Author author2 = TestDataUtil.generateTestAuthor(2L, "Bahez", 28);
        authorDao.create(author2);
        Book book2 = TestDataUtil.generateTestBook("978-9-234", "title", author1.getId());
        book2.setAuthorId(author2.getId());

        underTest.create(book1);
        underTest.create(book2);

        List<Book> results = underTest.findAll();
        assertThat(results).hasSize(2).containsExactly(book1, book2);
    }

    @Test
    public void testThatBooksCanBeUpdated() {
        Author author1 = TestDataUtil.createTestAuthor();
        authorDao.create(author1);

        Book book1 = TestDataUtil.createTestBook();
        book1.setAuthorId(author1.getId());
        underTest.create(book1);

        book1.setTitle("Updated title");
        underTest.update(book1.getIsbn(), book1);

        Optional<Book> results = underTest.find(book1.getIsbn());
        assertThat(results.isPresent());
        assertThat(results.get()).isEqualTo(book1);
    }

    @Test
    public void testThatBooksCanBeDeleted() {
        Author author1 = TestDataUtil.createTestAuthor();
        authorDao.create(author1);
        Book book1 = TestDataUtil.createTestBook();
        book1.setAuthorId(author1.getId());
        underTest.create(book1);
        underTest.delete(book1.getIsbn());
        Optional<Book> results = underTest.find(book1.getIsbn());
        assertThat(results.isPresent()).isFalse();
    }
}
