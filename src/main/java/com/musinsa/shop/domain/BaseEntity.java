package com.musinsa.shop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 해당 엔티티는 추상 클래스로서, 모든 엔티티에서 기본적으로 사용되는 필드들을 구성하여 필요한 엔티티는 이를 상속하여 사용한다.
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class) // createDate, updateDate 를 자동으로 기록.
public abstract class BaseEntity {

    // 자동생성 PK
    // UUID 를 사용하여 분산된 환경에서도 중복을 방지하며 ID 값을 예측 할 수 없게 만들어 보안 강화.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Hibernate 6.2 이상부터 GenericGenerator 에서 strategy 값에 String 사용을 권장하지 않음.
    private String id;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createDate; // 데이터 생성 일자를 기록.
    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime updateDate; // 데이터 마지막 변조 일자 기록.
}
