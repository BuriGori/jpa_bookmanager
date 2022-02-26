package com.example.jpa_bookmanager.repository;

import com.example.jpa_bookmanager.domain.User;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.comparator.JSONCompareUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@WebAppConfiguration
@SpringBootTest //spring context 를 활용해서 테스트하겠다는 뜻
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void crud() throws Exception{
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);

//        User user1 = userRepository.getById(1L);
//        System.out.println(user1);

        User user1 = userRepository.findById(1L).orElse(null);
        System.out.println(user1);

        Page<User> userPage = userRepository.findAll(PageRequest.of(1,3));

        System.out.println("page :"+userPage);
        System.out.println("getTotalElements :"+userPage.getTotalElements());
        System.out.println("getTotalPages :"+userPage.getTotalPages());
        System.out.println("getNumberOfElements :"+userPage.getNumberOfElements());
        System.out.println("getSort :"+userPage.getSort());
        System.out.println("getSize :"+userPage.getSize());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("name")
                .withMatcher("email",endsWith());//endWith() 말고  contains()도 있음

        Example<User> example = Example.of(new User("ma", "fastcampus.com") ,matcher);
        userRepository.findAll(example).forEach(System.out::println);
        //이렇게 매치하는 것도 있구나~


    }
    //findall() List<T> 타입으로 받게 되어있고
    //조건 없이 모든 값을 가져오므로 성능적인 이슈가 있으므로 잘 사용하지 않는다.
    //sort, List<long> id, 등등을 받아서 일부 정보만 가져오는 경우가 있다.
    //자세한 부분은 @JpaRepository 클래스에서 확인하면 된다.

    // springboot 2.5 ver 가 되면서 application-test에 설정을 변경해주어야 했다.
    // https://wenodev.tistory.com/25 참고

    // getby -> entity를 받아오고 findby는 optional 객체를 받아옴 따라서 getone으로 불러오기 위해서 세션을 유지해야하는데
    // @Transactional 을 추가해 줘야한다.
    // entity manager 에서 reference를 통해 레이지 패치를 사용

    // findby는 em에서 find를 통해서 바로 가져옴
    // optional 값

    // count값을 통해서 exist를 판단함

    // entity 를 통한 delete 과정은 select를 통해서 id를 찾고 delete를 찾는다.
    // deleteAll()을 사용하면 select를 통한 id들을 하나씩 delete문을 실행함
    // deleteInBatch를 사용하면 or 연산을 통해서 하나의 delete문이 실행
    // Batch를 사용하면 확인 작업을 거치지 않고 바로 kill
}
