package com.musinsa.shop.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 해당 엔티티는 브랜드 정보를 관리하며 SKU 와 양방향 관계를 갖는다.
 */
@Getter
@Setter
// 조회 성능 향상을 위해 name 컬럼에 인덱스를 생성
@Table(indexes = {
        @Index(columnList = "name")
})
@Entity
public class Brand extends BaseEntity{
    // 브랜드명
    private String name;

    // 하나의 브랜드는 N 개의 SKU 를 갖는다.
    @OneToMany(mappedBy = "brand")
    private Set<Sku> skuSet = new HashSet<>();

    // SKU 추가 기능
    public void addSku(Sku sku){
        sku.setBrand(this);
        getSkuSet().add(sku);
    }
}
