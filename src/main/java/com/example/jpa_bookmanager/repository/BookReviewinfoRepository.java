package com.example.jpa_bookmanager.repository;

import com.example.jpa_bookmanager.domain.BookReviewinfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewinfoRepository extends JpaRepository<BookReviewinfo, Long> {
}
