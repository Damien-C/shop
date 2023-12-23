package com.musinsa.shop.controller.rest;

import com.musinsa.shop.dto.ApiDataResponse;
import com.musinsa.shop.dto.ShopResponse;
import com.musinsa.shop.dto.SkuDto;
import com.musinsa.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@RestController
public class ApiShopController {

    private final ShopService shopService;

    // 1. 고객은 카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있어야 합니다.
    @GetMapping("/lowestPriceItems")
    public ApiDataResponse<ShopResponse> getLowestPriceItems(){
        return ApiDataResponse.of(shopService.getLowestPriceItems());
    }

    // 2. 고객은 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있어야 합니다.
    @GetMapping("/lowestPriceBrandItems")
    public ApiDataResponse<ShopResponse> getLowestPriceBrandItems(){
        return ApiDataResponse.of(shopService.getLowestPriceBrandItems());
    }

    // 3. 고객은 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 확인할 수 있어야 합니다.
    @GetMapping("/lowestAndHighestPriceItems")
    public ApiDataResponse<ShopResponse> getLowestAndHighestPriceItems(@RequestParam String categoryName){
        return ApiDataResponse.of(shopService.getLowestAndHighestPriceItems(categoryName));
    }


}
