package com.example.jpa_bookmanager.service;

import com.example.jpa_bookmanager.domain.User;
import com.example.jpa_bookmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class UserService {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void put(){
        User user = new User();
        user.setName("newUser");
        user.setEmail("newUser@fastcampus.com");

//        userRepository.save(user);
        entityManager.persist(user);

        entityManager.detach(user);
        //detach 하고 안하고 차이 알아야한다.
        user.setName("newUserAfterPersist");

        entityManager.merge(user);
        //merge하고 안하고도 알아야한다.
        //뭐든 JPA에서 save로 해결할 수 있기도함

        entityManager.flush();
        //보통 클리어 전에 플러쉬를 하는것을 권고한다.
        //+ 플러쉬 없을 때 생기는 문제.
        entityManager.clear();

        User user1 = userRepository.findById(1L).get();
        entityManager.remove(user1);

//        user1.setName("amasmam");
//        entityManager.merge(user1);
    }

}
