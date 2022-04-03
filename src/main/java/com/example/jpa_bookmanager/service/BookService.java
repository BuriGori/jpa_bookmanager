package com.example.jpa_bookmanager.service;

import com.example.jpa_bookmanager.domain.Author;
import com.example.jpa_bookmanager.domain.Book;
import com.example.jpa_bookmanager.repository.AuthorRepository;
import com.example.jpa_bookmanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final EntityManager entityManager;
    private final AuthorService authorService;
//    public void put(){
//        this.putBookAndAuthor();
//    }

    //Transcational 의 isolation => 트랜잭션의 격리 단계
    //Transcational 의 propagation => 트랜잭션의 교통정리 담당( 현재 트랜잭션과 다른 클래스의 트랜잭션의 차이 조절 )



    @Transactional(propagation = Propagation.REQUIRED)
    public void putBookAndAuthor(){
        // checkedE에 대해서도 rollbackFor 이라는 Transactional 옵션으로 롤백을 할 수도 있다.
        // 외부의 호출로 들어왔을 때는 @Transactional에 대해서 읽지 않고 실행을 한다.

        Book book = new Book();
        book.setName("Start JPA");

        bookRepository.save(book);
        try {
            authorService.putAuthor();
        }catch (RuntimeException e){

        }
//        throw new RuntimeException("error!! is ok transcation??");

//        Author author = new Author();
//        author.setName("martin");
//
//        authorRepository.save(author);
//
//        throw new RuntimeException("cant commit to db cause error");
        //RuntimeException(unchecked exception)과 달리 checkedE는 DB반영이 일어난다!
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void get(Long id){
        System.out.println(">>> "+ bookRepository.findById(id));
        System.out.println(">>> "+ bookRepository.findAll());

        entityManager.clear();//영속성 컨텍스트의 캐싱을 방지하기 위해서

        System.out.println(">>> "+ bookRepository.findById(id));
        System.out.println(">>> "+ bookRepository.findAll());

//        Book book = bookRepository.findById(id).get();
//        book.setName("change?");
//        bookRepository.save(book);

        bookRepository.update();
        entityManager.clear();



        // ISOLATION ==
        // Isolation을 하게 되면 중간에 발생하는 트랜잭션에 대해서 락이 걸리고 커밋 또는 롤백이 되면 다시 진행이 된다.
        // 하지만, 커밋이나 롤백이 되기 전에 데이터를 가져와서 Set을 하면 이미 엔티티에 적용이 되므로 소용이 없게 된다.
        // 이때, 엔티티를 업데이트하면 전체값을 새로 업데이트하기 때문에 name 뿐만 아니라 다른 컬럼값도 set이 적용되므로 커밋이나 롤백이 되지 않은 값도 적용이 되는 것이다.

        // 이를 해결하기 위해서 book entity에 @DynamicUpdate를 해주면 변경이 되는 값만 적용해서 update문이 적용되는데.
        // 여기서는 Name과 리스터 옵션을 걸어준 CreateAt만 Update가 일어난다.
        // 결국 (Isolation.READ_UNCOMMITED) 옵션은 잘 안써진다는 뜻.
        // Isolation.READ_COMMITED 옵션은 롤백된 값에 대해서는 적용시키지 않는 것을 확인할 수 있다. (dirty read 현상을 없앨 수 있다.)
        // 하지만, 같은 값을 여러번 조회하는 경우 중간에 값이 바뀔 수 있는 현상이 발생한다. -> 이 부분은 커밋이 발생하면 데이터의 정확성이 보장이 되지 않음.

        // 이를 해결하기 위해서 나온 것이 Isolation.REPEATABLE_READ 이다.
        // 트랜잭션 내에서는 조회했던 값을 다시 호출할때 이전 기록을 다시 불러주는 것을 뜻한다. -> 트랜잭션 내에서 값의 변경이 없지만, 이후에 적용되는 것을 알 수 있다.
        // 팬텀리드가 발생할 수도 있다??? -> 로우 값을 하나 추가 (Book객체를 하나 더 추가) 하고 업데이트문을 돌리면
        // 보이지 않는 값에도 업데이트가 적용되는 것을 확인 할 수 있다..

        // 이를 해결하기 위해서 나온 것이 Isolation.SEARIALIZABLE
        // 커밋이 일어나지 않은 트랜잭션이 있으면 락을 걸고 커밋을 완료한 후에 진행하게 만든다..
        // 데이터가 변경하게 되려면 트랜잭션을 마무리하게 만들기 때문에 데이터 정확성은 100%가 되지만 그만큼 성능이 떨어진다.


        // PROPAGATION===
        // Required(default) => 트랜잭션이 있으면 재활용, 없다면 내가 생성하겠다라는 뜻.
        // Transactional이라는 어노테이션이 없을 때 save() 함수로 트랜잭션이 실행됬던 것처럼..
        // 하지만, 재활용하여 하나의 트랜잭션으로 관리하게 된다면 작은 오류로 모든 트랜잭션이 망가질 수 있다!

        //Required_new 옵션은 있든 말든 새로 만든다는 것이기 때문에 분리를 해낼 수 있다.

        //Required_nested 는 사용하던 트랜잭션을 같이 사용하되 살짝 분리해서 사용하는 느낌! -> 브랜치!?
        //nested가 오류가 나면 nested만 롤백
        //하지만 사용하는 마스터 트랜잭션이 오류 뜨면 전체 롤백 --벗 그렇게 생각대로 안되서 잘 사용하지 않는다.

        //Support -> 호출한 쪽에서 트랜잭션이 있다면 사용, 없으면 사용하지 않고 진행..
        //Notsupport -> 호출한 쪽에서 트랜잭션이 있다면 잠시 멈추고 트랜잭선이 완료된 이후에 트랜잭션 없이 진행을 한다.

        //Mandatory -> 이미 생성된 트랜잭션이 반드시 있어야하고 없다면 오류를 발생시킨다.
        //Never -> 트랜잭션이 없어야 한다. 있다면 오류를 발생시킨다.

        // 보통 Required 만을 사용하는 경우가 많다.
        // 유지보수를 하는 경우 문제가 발생할 가능성이 있다.
        // 일관된 구조가 중요하다..

        // Transactional의 클래스 스콥, 메소드 스콥이 나눠진 것을 확인해야한다.!


    }
}
