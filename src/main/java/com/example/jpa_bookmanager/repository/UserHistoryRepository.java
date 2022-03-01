package com.example.jpa_bookmanager.repository;

import com.example.jpa_bookmanager.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

}
