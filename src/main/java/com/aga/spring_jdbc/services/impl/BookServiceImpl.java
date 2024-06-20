package com.aga.spring_jdbc.services.impl;

import com.aga.spring_jdbc.domain.Book;
import com.aga.spring_jdbc.repositories.BookRepository;
import com.aga.spring_jdbc.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.listBooks();
    }
}
