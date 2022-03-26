package com.example.jpa_bookmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class JpaBookmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaBookmanagerApplication.class, args);
    }

}
