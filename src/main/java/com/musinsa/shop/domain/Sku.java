package com.musinsa.shop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


/**
 * Stock Keeping Unit 은 상품 재고의 최소 단위로, 각 브랜드의 상품에 해당하는 카테고리 아이템을 의미한다.
 * 따라서, 해당 엔티티는 브랜드와 카테고리의 N:N 문제를 맵핑하여 해결 함과 동시에 아이템의 수량(과제에서는 제외)과 가격을 정의할 수 있다.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 조회 성능 향상을 위해 price 컬럼에 인덱스를 생성
@Table(indexes = {
        @Index(columnList = "price")
})
@Entity
public class Sku extends BaseEntity{

    // SKU 의 가격, 최대 1억원까지 저장할 수 있고 원(KRW) 단위는 소수점이 없기때문에 scale 은 0으로 저장한다.
    @Column(precision = 9, scale = 0)
    protected BigDecimal price;

    // 하나의 브랜드는 N개의 SKU 를 갖는다.
    @ManyToOne
    protected Brand brand;

    // 하나의 카테고리는 N개의 SKU 를 갖는다.
    @ManyToOne
    protected Category category;

}
