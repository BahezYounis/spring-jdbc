package com.aga.spring_jdbc.daos;

import com.aga.spring_jdbc.domain.Book;

import java.util.List;

public interface BookDao {

    List<Book> listBooks();
}
