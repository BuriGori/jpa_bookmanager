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

//    @Transactional
    @Test
    void bookCascadeTest(){
        Book book = new Book();
        book.setName("JPA STUDYY");

//        bookRepository.save(book);

        Publisher publisher = new Publisher();
        publisher.setName("FastCampus");

//        publisherRepository.save(publisher);
        //중간중간에 save를 자주 하는 것은 좋지 않다.

        book.setPublisher(publisher);
        bookRepository.save(book);

//        publisher.getBooks().add(book);
//        publisher.addBook(book);
//        publisherRepository.save(publisher);

        System.out.println("Book: " + bookRepository.findAll());
        System.out.println("Publisher: "+publisherRepository.findAll());

        Book book1 = bookRepository.findById(1L).get();
        book1.getPublisher().setName("slow");
        bookRepository.save(book1);
        System.out.println("publishers :"+ publisherRepository.findAll());


        // cascade를 사용하면서 연관관계를 정의할때 적용이 되는 벙위를 지정한다.
        // persist옵션은 저장을 하는 경우에 연관관계를 이어준다고 명시하는 것이다.
        // 따라서 merge에 대한 연관관계를 이어줄 때는 옵션을 추가해주면 된다.( []형태라서 추가 해주면 된다. )
        // 연관관계를 전이시킨다는 의미가 더 맞다. book에서만 persist하고 update 하는게 publisher한테도 전이시키겠다는 것.
        // detach 영속성을 관리하지 않겠다는 것.
        // refresh 연관관계가 있는 엔티티를 다시 로딩할때 함께 재 로딩하겠다는 것.
        // default는 {} 이기때문에 영속성 전이가 전혀 되지 않았다는 것이다!. => ALL 을 사용해서 모든 전이를 허용하기도 한다.
        // Remove는 자주 사용하지만 위험하기 때문에 주의가 필요


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