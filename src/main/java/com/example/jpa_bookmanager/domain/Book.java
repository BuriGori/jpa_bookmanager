package com.example.jpa_bookmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = MyEntityListener.class)
public class Book implements Auditable{
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String  author;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

//    @PrePersist
//    public void prePersist(){
//        this.createdAt=LocalDateTime.now();
//        this.updatedAt= LocalDateTime.now();
//        return;
//    }
//
//    @PreUpdate
//    public void preUpdate(){
//        this.updatedAt=LocalDateTime.now();
//    }
}
