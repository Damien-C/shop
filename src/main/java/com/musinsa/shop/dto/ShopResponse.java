package com.musinsa.shop.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 유저 API 응답을 공통적으로 사용할 수 있는 Response 객체
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopResponse {
    private BigDecimal totalPrice;          // 구현 1) 총액 값
    private PriceDto lowestPrice;           // 구현 2) 최저가 값을 객체로 반환
    private String category;                // 구현 3) 카테고리 값
    private List<?> lowestPriceList;        // 구현 3) 최저가 응답값 타입이 리스트 (구현 1과 공통으로 사용)
    private List<SkuDto> highestPriceList;  // 구현 3) 최고가 응답값 타입이 리스트
}

