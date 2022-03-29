package com.example.jpa_bookmanager.service;

import com.example.jpa_bookmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Test
    void test(){
        userService.put();
//        System.out.println(" >>> " + userRepository.findByemail("newUser@fastcampus.com") );
        userRepository.findAll().forEach(System.out::println);
    }
}