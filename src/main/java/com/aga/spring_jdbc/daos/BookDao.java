package com.aga.spring_jdbc.daos;

import com.aga.spring_jdbc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    List<Book> listBooks();

    void create(Book book);

    Optional<Book> find(String isbn);

    List<Book> findAll();

    void update(String isbn, Book book);

    void delete(String isbn);
}
