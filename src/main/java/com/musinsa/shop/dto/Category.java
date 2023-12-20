package com.musinsa.shop.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 해당 엔티티는 상품의 카테고리 정보를 관리하며 SKU 와 양방향 관계를 갖는다.
 */
@Getter
// 조회 성능 향상을 위해 name 컬럼에 인덱스를 생성
@Table(indexes = {
        @Index(columnList = "name")
})
@Entity
public class Category extends BaseEntity{

    // 카테고리명
    @Setter
    private String name;

    // 하나의 카테고리는 N 개의 SKU 를 갖는다.
    @OneToMany(mappedBy = "category")
    private Set<Sku> skuSet = new HashSet<>();

    // 양방향 관계 형성시
    public void add(Sku sku){
        sku.setCategory(this);
        getSkuSet().add(sku);
    }
}
