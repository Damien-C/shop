package com.musinsa.shop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * 해당 엔티티는 브랜드 정보를 관리하며 SKU 와 양방향 관계를 갖는다.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 조회 성능 향상을 위해 name 컬럼에 인덱스를 생성
@Table(indexes = {
        @Index(columnList = "name")
})
@Entity
public class Brand extends BaseEntity{
    // 브랜드명
    private String name;

    public Brand(String name){
        this.name = name;
    }

    // 하나의 브랜드는 N 개의 SKU 를 갖는다.
    // 브랜드 삭제시 브랜드에 속해있는 Sku 도 함께 삭제한다.
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Sku> skuSet = new HashSet<>();

}
