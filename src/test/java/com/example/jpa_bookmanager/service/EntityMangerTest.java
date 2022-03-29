package com.example.jpa_bookmanager.service;

import com.example.jpa_bookmanager.domain.User;
import com.example.jpa_bookmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class EntityMangerTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Test
    void entityManagerTeset(){
        System.out.println(entityManager.createQuery("select u from User u").getResultList());
        //userRepository.findAll()과 같다.
    }

    @Test
    void cacheFindTest(){
        System.out.println(userRepository.findById(1L).get());
        System.out.println(userRepository.findById(1L).get());
        System.out.println(userRepository.findById(1L).get());
        System.out.println(userRepository.findById(1L).get());
    }

    @Test
    void cacheTest2(){
        User user = userRepository.findById(1L).get();
        user.setName("marrrrrrrrrrr");
        userRepository.save(user);
//        userRepository.flush();
        System.out.println("---------------");

        user.setEmail("marrrrr@fast.com");
        userRepository.save(user);
//        userRepository.flush();
        System.out.println("-------------");

//        System.out.println(">>>> 1 :" + userRepository.findById(1L).get());
//        userRepository.flush();
//        System.out.println(">>>> 2 :" + userRepository.findById(1L).get());
//
        System.out.println(userRepository.findAll());
    }

}
