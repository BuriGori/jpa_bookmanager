package com.example.jpa_bookmanager.repository;

import com.example.jpa_bookmanager.domain.Book;
import com.example.jpa_bookmanager.domain.Publisher;
import com.example.jpa_bookmanager.domain.Review;
import com.example.jpa_bookmanager.domain.User;
import org.hibernate.annotations.SortComparator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("???");
        book.setAuthorId(1L);
//        book.setPublisherId(1L);

        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
    }

    @Test
    @Transactional
    void bookRelationTest(){
        givenBookAndReview();
        User user = userRepository.findByemail("martin@fastcampus.com");
        System.out.println("Review : " +user.getReviews());
        System.out.println("Book : "+ user.getReviews().get(0).getBook());
        System.out.println("Publisher : "+ user.getReviews().get(0).getBook().getPublisher());
    }

    private void givenBookAndReview(){
        givenReview(givenUser(), givenBook(givenPublisher()));
    }

    private Book givenBook(Publisher publisher) {
        Book book = new Book();
        book.setName("JPA Book name");
        book.setPublisher(publisher);
        return bookRepository.save(book);
    }

    private User givenUser(){
        return userRepository.findByemail("martin@fastcampus.com");
    }

    private void givenReview(User user, Book book) {
        Review review = new Review();
        review.setTitle("Review Title");
        review.setContent("Review ... start .. mid.. end");
        review.setScore(5.0f);
        review.setUser(user);
        review.setBook(book);
        reviewRepository.save(review);
    }

    private Publisher givenPublisher(){
        Publisher publisher = new Publisher();
        publisher.setName("APublisher");
        return publisherRepository.save(publisher);
    }
}