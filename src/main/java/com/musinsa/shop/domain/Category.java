package com.musinsa.shop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * 해당 엔티티는 상품의 카테고리 정보를 관리하며 SKU 와 양방향 관계를 갖는다.
 * 과제에서는 카테고리 8개가 고정이고 변동사항이 없어서 Enum 클래스로 대체 가능하나,
 * 실제 현업에서의 사용되는 설계 방식대로 구현하였다.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 조회 성능 향상을 위해 name 컬럼에 인덱스를 생성
@Table(indexes = {
        @Index(columnList = "name")
})
@Entity
public class Category extends BaseEntity{

    public Category(String name){
        this.name = name;
    }

    // 카테고리명
    @Setter
    private String name;

    // 하나의 카테고리는 N 개의 SKU 를 갖는다.
    // 카테고리 삭제시 카테고리에 속해있는 Sku 도 함께 삭제한다.
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Sku> skuSet = new HashSet<>();

}
