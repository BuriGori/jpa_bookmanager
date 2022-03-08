package com.example.jpa_bookmanager.repository;

import com.example.jpa_bookmanager.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
