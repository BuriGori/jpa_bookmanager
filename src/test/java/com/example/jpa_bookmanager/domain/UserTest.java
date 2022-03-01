package com.example.jpa_bookmanager.domain;

import org.hibernate.boot.jaxb.internal.stax.LocalSchemaLocator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void test(){
        User user = new User();
        user.setEmail("martin@fastcampus.com");
        user.setName("martin");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        System.out.println(">>> "+ user.toString());

//        User user1 = new User( null ,"martin", "martin@slowcampus.com",LocalDateTime.now(), LocalDateTime.now());

        User user2 = User.builder()
                .name("martin")
                .email("martin@slow.com")
                .build();

        //notNull = NULL만 허용하지 않음
        //notEmpty = NULL과 ""를 허용하지 않음 " "는 허용
        //notBlank = NULL과 "", " "를 허용하지 않음
        //nonNull = RequiredArgsConstructor의 생성자를 받는 어노테이션 + final도 받음
    }
}