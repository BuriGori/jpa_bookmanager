package com.example.jpa_bookmanager.domain;

import com.example.jpa_bookmanager.domain.listener.Auditable;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(value = AuditingEntityListener.class)
public class Book extends BaseEntity implements Auditable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String  author;

//    @CreatedDate
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    private LocalDateTime updatedAt;

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
