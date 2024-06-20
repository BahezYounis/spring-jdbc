package com.aga.spring_jdbc.daos;

import com.aga.spring_jdbc.domain.Author;

import java.util.Optional;

public interface AuthorDao {

    Optional<Author> getAuthorByBookIsbn(String isbn);
}
