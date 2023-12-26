package com.musinsa.shop.controller.rest;

import com.musinsa.shop.constant.StatusCode;
import com.musinsa.shop.dto.PriceDto;
import com.musinsa.shop.dto.ShopResponse;
import com.musinsa.shop.dto.SkuDto;
import com.musinsa.shop.exception.GeneralApiException;
import com.musinsa.shop.service.ShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@DisplayName("ApiShopController Test")
@WebMvcTest(ApiShopController.class)
class ApiShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @Test
    @DisplayName("[API][GET] 구현 1의 응답객체 구조 테스트")
    void getLowestPriceItemsTest() throws Exception {
        // Given
        List<SkuDto> lowestPriceList = List.of(
                new SkuDto("1", "C", "상의", new BigDecimal("10000"))
        );

        ShopResponse mockResponse = ShopResponse.builder()
                .totalPrice(new BigDecimal("34100"))
                .lowestPriceList(lowestPriceList)
                .build();
        given(shopService.getLowestPriceItems()).willReturn(mockResponse);

        // When & Then
        // When & Then
        mockMvc.perform(get("/api/lowestPriceItems")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalPrice").value(34100))
                .andExpect(jsonPath("$.data.lowestPriceList", hasSize(1)))
                .andExpect(jsonPath("$.data.lowestPriceList[0].category").value("상의"))
                .andExpect(jsonPath("$.data.lowestPriceList[0].price").value(10000))
                .andExpect(jsonPath("$.data.lowestPriceList[0].brand").value("C"));
    }

    @Test
    @DisplayName("[API][GET] 구현 2의 응답객체 구조 테스트")
    void getLowestPriceBrandItemsTest() throws Exception {
        // Given
        PriceDto lowestPrice = PriceDto.builder()
                .brand("D")
                .category(List.of(
                        new SkuDto(null, null, "상의", new BigDecimal("10100"))
                ))
                .totalPrice(new BigDecimal("10100"))
                .build();

        ShopResponse mockResponse = ShopResponse.builder()
                .lowestPrice(lowestPrice)
                .build();

        given(shopService.getLowestPriceBrandItems()).willReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/lowestPriceBrandItems")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.lowestPrice.brand").value("D"))
                .andExpect(jsonPath("$.data.lowestPrice.category", hasSize(1)))
                .andExpect(jsonPath("$.data.lowestPrice.category[0].category").value("상의"))
                .andExpect(jsonPath("$.data.lowestPrice.category[0].price").value(10100))
                .andExpect(jsonPath("$.data.lowestPrice.totalPrice").value(10100));
    }

    @Test
    @DisplayName("[API][GET] 구현 3의 응답객체 구조 테스트")
    void getLowestAndHighestPriceItemsTest() throws Exception {
        // Given
        String categoryName = "상의";
        List<SkuDto> lowestPriceList = Collections.singletonList(
                new SkuDto(null, "C", null, new BigDecimal("10000"))
        );
        List<SkuDto> highestPriceList = Collections.singletonList(
                new SkuDto(null, "I", null, new BigDecimal("11400"))
        );

        ShopResponse mockResponse = ShopResponse.builder()
                .category(categoryName)
                .lowestPriceList(lowestPriceList)
                .highestPriceList(highestPriceList)
                .build();
        given(shopService.getLowestAndHighestPriceItems(categoryName)).willReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/lowestAndHighestPriceItems")
                        .contentType(APPLICATION_JSON)
                        .param("categoryName", categoryName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.category").value(categoryName))
                .andExpect(jsonPath("$.data.lowestPriceList", hasSize(1)))
                .andExpect(jsonPath("$.data.lowestPriceList[0].brand").value("C"))
                .andExpect(jsonPath("$.data.lowestPriceList[0].price").value(10000))
                .andExpect(jsonPath("$.data.highestPriceList", hasSize(1)))
                .andExpect(jsonPath("$.data.highestPriceList[0].brand").value("I"))
                .andExpect(jsonPath("$.data.highestPriceList[0].price").value(11400));
    }

    @Test
    @DisplayName("[API][GET] 구현 3 존재하지 않는 카테고리 이름으로 최저가와 최고가 브랜드 조회")
    void getLowestAndHighestPriceItemsCategoryNotFoundTest() throws Exception {
        // Given
        String nonExistingCategoryName = "없는카테고리";
        given(shopService.getLowestAndHighestPriceItems(nonExistingCategoryName))
                .willThrow(new GeneralApiException(StatusCode.CATEGORY_NAME_NOT_FOUND));

        // When & Then
        mockMvc.perform(get("/api/lowestAndHighestPriceItems")
                        .param("categoryName", nonExistingCategoryName)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("CATEGORY_NAME_NOT_FOUND"));
    }

    @Test
    @DisplayName("[API][GET] 전체 상품 조회")
    void getAllItemsTest() throws Exception {
        // Given
        List<SkuDto> allItems = Arrays.asList(
                new SkuDto("1", "A", "상의", new BigDecimal("11200")),
                new SkuDto("2", "A", "아우터", new BigDecimal("5500"))
        );
        given(shopService.getAllSkuItems()).willReturn(allItems);

        // When & Then
        mockMvc.perform(get("/api/allItems")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value("1"))
                .andExpect(jsonPath("$.data[0].brand").value("A"))
                .andExpect(jsonPath("$.data[0].category").value("상의"))
                .andExpect(jsonPath("$.data[0].price").value(11200))
                .andExpect(jsonPath("$.data[1].id").value("2"))
                .andExpect(jsonPath("$.data[1].brand").value("A"))
                .andExpect(jsonPath("$.data[1].category").value("아우터"))
                .andExpect(jsonPath("$.data[1].price").value(5500));
    }

    @Test
    @DisplayName("[API][GET] 존재하지 않는 경로 요청")
    void getNotFoundTest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/notfound")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("CLIENT_ERROR"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."));
    }
}