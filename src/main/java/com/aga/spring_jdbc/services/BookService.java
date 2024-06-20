package com.aga.spring_jdbc.services;

import com.aga.spring_jdbc.domain.Book;

import java.util.List;


public interface BookService {

    List<Book> getBooks();
}
