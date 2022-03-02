package com.example.jpa_bookmanager.repository;

import com.example.jpa_bookmanager.domain.Book;
import org.hibernate.annotations.SortComparator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("???");
        book.setAuthorId(1L);
        book.setPublisherId(1L);

        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
    }
}