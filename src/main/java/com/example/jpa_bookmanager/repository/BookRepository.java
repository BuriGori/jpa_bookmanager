package com.example.jpa_bookmanager.repository;

import com.example.jpa_bookmanager.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface BookRepository extends JpaRepository<Book,Long> {
}
