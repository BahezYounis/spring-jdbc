package com.aga.spring_jdbc.repositories.impl;

import com.aga.spring_jdbc.daos.AuthorDao;
import com.aga.spring_jdbc.daos.BookDao;
import com.aga.spring_jdbc.domain.Author;
import com.aga.spring_jdbc.domain.Book;
import com.aga.spring_jdbc.repositories.BookRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    public BookRepositoryImpl(BookDao bookDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }
    @Override
    public List<Book> listBooks() {
         return bookDao.listBooks().stream().map( book -> {
            final Author author = authorDao.getAuthorByBookIsbn(book.getIsbn()).orElse(null);
            book.setAuthor(author);
            return book;
        }).collect(Collectors.toList());
    }
}
