package com.example.jpa_bookmanager.repository;

import com.example.jpa_bookmanager.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);
    //단순 User를 리턴하는 값은 여러개의 값이 존재할 때 문제가 발생할 수 있으므로 List형으로 만든다
    //Optional, Set 과 같은 리턴형으로 받을 수 있다.
    User findByemail(String email);
    // find 이외에도 get, read, query, serach, stream + by 와 같이 사용할 수 있다.


    List<User> findFirst1ByName(String name);
    List<User> findTop2ByName(String name);
    //first, top에 숫자를 줘서 개수를 조정할 수도 있다. Last는 없다..

    List<User> findByNameAndEmail(String name, String email);
    //and 와 or 모두 사용이 가능하다.

    List<User> findByCreatedAtAfter(LocalDateTime date);
    //크다 작다로도 사용이 가능하지만 GreaterThan, GreaterThenEqual 로도 사용이 가능하다.(After, Before는 자신을 포함하지 않음)
    //또한 Between 을 활용해서 값사이, 날짜사이 등을 확인할 수 있다.(양 끝단을 포함)

    List<User> findByIdIsNotNull();
//    List<User> findByAddressIsNotEmpty();
    //isNotEmpty => 자주 사용하지 않는다..


    List<User> findByNameIn(List<String> names);
    //포함되어 있는 리스트

    List<User> findByNameStartingWith(String name);
    //EndingWith, Contains, Like 와 같은 기능도 사용할 수 있다. (이름에서 유추 가능)
    //Like 에서는 %를 추가해줘야하는데 이를 각 함수로 편하게 구현이 가능

    List<User> findTopByNameOrderByIdDesc(String name);
    //Last가 없다고 했지만 이렇게 정렬로 구현이 가능하다.
    //And 키워드를 통해서 여러가지 조건을 추가가능
    //JPArepo에 상속받는 것 중 PagingAndSortingRepo가 있다.. 찾아보자
    List<User> findFirstByName(String name, Sort sort);
    //Sort 인자로 정렬을 줄 수 있다.

    Page<User> findByName(String name, Pageable pageable);

    @Query(value = "select * from user limit 1;", nativeQuery = true)
    Map<String, Object> findRawRecord();
    //Query Anotation을 쓰면 위 쿼리의 결과값이 밑에 함수에 전달된다.
}
