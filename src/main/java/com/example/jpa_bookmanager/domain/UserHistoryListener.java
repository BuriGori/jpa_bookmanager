package com.example.jpa_bookmanager.domain;

import com.example.jpa_bookmanager.repository.UserHistoryRepository;
import com.example.jpa_bookmanager.support.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class UserHistoryListener {
    @PrePersist
    @PreUpdate
    public void prePersistAndPreUpdate(Object o){
        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);

        User user = (User) o;

        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(user.getId());
        userHistory.setName(user.getName());
        userHistory.setEmail(userHistory.getEmail());

        userHistoryRepository.save(userHistory);
    }
}
