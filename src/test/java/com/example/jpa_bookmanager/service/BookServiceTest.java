package com.example.jpa_bookmanager.service;

import com.example.jpa_bookmanager.repository.AuthorRepository;
import com.example.jpa_bookmanager.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void transactionTest(){
        try {
            bookService.putBookAndAuthor();
        } catch (Exception e){// RuntimeException과 비교하는 과정
            System.out.println(">>>>"+ e.getMessage());
        }

        System.out.println("book : " + bookRepository.findAll());
        System.out.println("author : " + authorRepository.findAll());
    }


}