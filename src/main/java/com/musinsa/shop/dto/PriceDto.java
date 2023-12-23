package com.musinsa.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 최고가, 최소가의 값을 담을때 사용하는 DTO 객체
 */
@Setter
@Getter
@Builder
public class PriceDto {
    private String brand;              // 구현 2) 브랜드 값
    private List<SkuDto> category;     // 구현 2) 카테고리 값
    private BigDecimal totalPrice;     // 구현 2) 총액 값
}
