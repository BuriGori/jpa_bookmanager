package com.example.jpa_bookmanager.domain;

import com.example.jpa_bookmanager.domain.listener.Auditable;
import com.example.jpa_bookmanager.domain.listener.UserHistoryListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@EntityListeners(value = { UserHistoryListener.class})
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

//    @CreatedDate
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    private LocalDateTime updatedAt;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    //Enumerated가 default로 Ordinal로 되어있기 때문에 index값으로 불러온다.
    //하지만 Enum에 값이 추가되면 문제가 생길 수 있기 때문에 꼭 StringType으로 변경해주어야 한다.

    //    @OneToMany(fetch = FetchType.EAGER)
    //    private List<Address> address;

    //    @Transient
    //    private String testData;
    //DB에서 처리하지 않지만 사용하응 데이터

    //Listener -> @PrePersist, @PostUpdate, @PostLoad 등등이 있다.

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false) //엔티티가 어느 테이블로 조인되는지 확인하는 것 + readonly로 만들어주는 @
    @ToString.Exclude
    private List<UserHistory> userHistories = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();
}
