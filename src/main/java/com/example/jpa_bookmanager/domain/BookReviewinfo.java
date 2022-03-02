package com.example.jpa_bookmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class BookReviewinfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long bookId;
    @OneToOne(optional = false)
    private Book book;

    private float averageReviewScore;

    private int reviewCount;

    //float, int 는 기본값을 0 으로 하기 위해서
    //Float, Integer 는 Null 체크를 해줘야함
}
