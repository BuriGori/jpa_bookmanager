package com.example.jpa_bookmanager.repository;

import com.example.jpa_bookmanager.domain.Book;
import com.example.jpa_bookmanager.domain.BookReviewinfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookReviewinfoRepositoryTest {
    @Autowired
    private BookReviewinfoRepository bookReviewinfoRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void crudTest(){
        BookReviewinfo bookReviewinfo = new BookReviewinfo();
//        bookReviewinfo.setBookId(1L);
        bookReviewinfo.setAverageReviewScore(4.5f);
        bookReviewinfo.setReviewCount(2);

        bookReviewinfoRepository.save(bookReviewinfo);

        System.out.println(">>>> " + bookReviewinfoRepository.findAll());
    }

    @Test
    void crudTest2(){
        givenBook();
        givenBookReviewInfo();
        System.out.println(">>> " + bookReviewinfoRepository.findAll());

        Book result = bookReviewinfoRepository
                        .findById(1L)
                        .orElseThrow(RuntimeException::new)
                        .getBook();

        System.out.println(">>> "+result);
    }

    private Book givenBook(){
        Book book = new Book();
        book.setName("JPA");
        book.setAuthorId(1L);
        book.setPublisherId(1L);

        return bookRepository.save(book);
    }

    private void givenBookReviewInfo(){
        BookReviewinfo bookReviewinfo = new BookReviewinfo();
        bookReviewinfo.setBook(givenBook());
        bookReviewinfo.setAverageReviewScore(4.5f);
        bookReviewinfo.setReviewCount(2);

        bookReviewinfoRepository.save(bookReviewinfo);
    }
}