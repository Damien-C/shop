package com.musinsa.shop.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * SKU 값을 전달할때 공통적으로 사용 할 수 있는 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkuDto {
    private String id;          // 아이디
    private String brand;       // 브랜드 값
    private String category;    // 카테고리 값
    private BigDecimal price;   // 가격 값

    public SkuDto(String id){
        this.id = id;
    }

}
