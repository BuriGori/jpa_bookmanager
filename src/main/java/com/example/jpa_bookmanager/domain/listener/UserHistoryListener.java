package com.example.jpa_bookmanager.domain.listener;

import com.example.jpa_bookmanager.domain.User;
import com.example.jpa_bookmanager.domain.UserHistory;
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
        userHistory.setName(user.getName());
        userHistory.setEmail(userHistory.getEmail());
        userHistory.setUser(user);

        userHistoryRepository.save(userHistory);
    }
}
