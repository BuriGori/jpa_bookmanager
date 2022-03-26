package com.example.jpa_bookmanager.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfiguration {
    // main함수에서 @EnableJpaAuditing 부분을 훔쳐오고
    // 사용함으로써 슬라이스 테스트인 HelloTest()를 적용 시켜줌
}
