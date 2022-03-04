package com.example.jpa_bookmanager.repository;

import com.example.jpa_bookmanager.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
