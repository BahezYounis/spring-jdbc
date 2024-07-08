package com.aga.spring_jdbc.daos;

import com.aga.spring_jdbc.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Optional<Author> getAuthorByBookIsbn(String isbn);

    void create(Author author);

    Optional<Author> findOne(long l);

    List<Author> findAll();

    void update(Long id, Author author);

    void delete(long id);
}
