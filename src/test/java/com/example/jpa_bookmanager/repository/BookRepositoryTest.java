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

        Book book2 = bookRepository.findById(1L).get();
//        bookRepository.delete(book2);
//        bookRepository.deleteById(1L);
//        publisherRepository.delete(book2.getPublisher());

        Book book3 = bookRepository.findById(1L).get();
        book3.setPublisher(null);

        bookRepository.save(book3);

        System.out.println("Book: " + bookRepository.findAll());
        System.out.println("Publisher: "+publisherRepository.findAll());
        System.out.println("book3 - publisher: "+bookRepository.findById(1L).get().getPublisher());

        // cascade를 사용하면서 연관관계를 정의할때 적용이 되는 벙위를 지정한다.
        // persist옵션은 저장을 하는 경우에 연관관계를 이어준다고 명시하는 것이다.
        // 따라서 merge에 대한 연관관계를 이어줄 때는 옵션을 추가해주면 된다.( []형태라서 추가 해주면 된다. )
        // 연관관계를 전이시킨다는 의미가 더 맞다. book에서만 persist하고 update 하는게 publisher한테도 전이시키겠다는 것.
        // detach 영속성을 관리하지 않겠다는 것.
        // refresh 연관관계가 있는 엔티티를 다시 로딩할때 함께 재 로딩하겠다는 것.
        // default는 {} 이기때문에 영속성 전이가 전혀 되지 않았다는 것이다!. => ALL 을 사용해서 모든 전이를 허용하기도 한다.
        // Remove는 자주 사용하지만 위험하기 때문에 주의가 필요

        // Remove를 하지 않으면 엔티티를 삭제하더라도 엔티티와 연관된 데이터는 살아있어서 문제가 생길 수 있다.
        // 또한, 연관관계를 없애기 위해서 연관관계에 있는 변수의 값을 null로 세팅하면 연관관계가 정리된다.
        // 하지만 변경했다고 연관관계가 아예 끊어진 것은 아니다.
        // OneToMany의 orphanRemoval = true 옵션은 null로 설정했을 때 관련된 엔티티를 같이 삭제할 것이면 사용하는 것이고
        // cascade remove의 경우는 실제로 삭제하는경우에 다같이 삭제를 하지만 null로 설정했을 때는 관련된 엔티티를 삭제하지는 않는다.
        //
    }

    @Test
    void bookRemoveCascadeTest(){
        bookRepository.deleteById(1L);

        System.out.println("Book: " + bookRepository.findAll());
        System.out.println("Publisher: "+publisherRepository.findAll());
        bookRepository.findAll().forEach(book -> System.out.println(book.getPublisher()));

        // data.sql은 단순 쿼리문이기 때문에 이전에 설정한 리스너들을 적용하지 않기 때문에 생성, 수정 날짜가 반영되지 않는다.
        // remove가 적용되었기 때문에 모든 연관관계가 끊어진 것을 확인할 수 있다.!!

    }

    @Test
    void softDelete(){
        bookRepository.findAll().forEach(System.out::println);
        System.out.println(bookRepository.findById(3L));

        bookRepository.findByCategoryIsNull().forEach(System.out::println);

        bookRepository.findAllByDeletedFalse().forEach(System.out::println);

        bookRepository.findByCategoryIsNullAndDeletedFalse().forEach(System.out::println);

        // Book 엔티티에 deleted 라는 Boolean 값을 넣어서 처리하는 경우도 있는데.
        // 그렇게 되면 삭제될 값도 불러와서 쓸 수 있다는 장점이 있지만
        // 위와 같이 모든 저장소 메소드에 DeletedFalse라는 값이 들어가서 불편함을 야기할 수 있다.
        // 그래서 Book 엔티티의 어노테이션을 @Where을 달아서
        // 항상 실행되는 조건절을 달아줘서 항상 삭제되지 않은 값을 조회하되 삭제되는 엔티티는 soft하게 가져가는 방법이 있다.
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