package com.musinsa.shop.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.shop.constant.StatusCode;
import com.musinsa.shop.dto.ShopRequest;
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

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@DisplayName("ApiShopAdminController Test")
@WebMvcTest(ApiShopAdminController.class)
class ApiShopAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @DisplayName("[API][POST] 브랜드 등록")
    void createBrandTest() throws Exception {
        // Given
        String brandName = "새브랜드";
        ShopRequest request = new ShopRequest();
        request.setBrandName(brandName);

        // When & Then
        mockMvc.perform(post("/admin/api/brand")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.statusCode").value("NORMAL"))
                .andExpect(jsonPath("$.message").value("정상"));

        then(shopService).should().createBrand(brandName);
    }

    @Test
    @DisplayName("[API][POST] 같은 브랜드 이름이 이미 존재하는 경우 브랜드 등록 실패")
    void createBrandWithExistingNameTest() throws Exception {
        // Given
        String existingBrandName = "존재하는브랜드";
        ShopRequest request = new ShopRequest();
        request.setBrandName(existingBrandName);

        doThrow(new GeneralApiException(StatusCode.BRAND_NAME_ALREADY_EXISTS))
                .when(shopService)
                .createBrand(existingBrandName);

        // When & Then
        mockMvc.perform(post("/admin/api/brand")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("BRAND_NAME_ALREADY_EXISTS"));

        then(shopService).should().createBrand(existingBrandName);
    }

    @Test
    @DisplayName("[API][DELETE] 브랜드 삭제")
    void deleteBrandByNameTest() throws Exception {
        // Given
        String brandName = "삭제할브랜드";
        ShopRequest request = new ShopRequest();
        request.setBrandName(brandName);

        // When & Then
        mockMvc.perform(delete("/admin/api/brand")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.statusCode").value("NORMAL"))
                .andExpect(jsonPath("$.message").value("정상"));

        then(shopService).should().deleteBrand(brandName);
    }

    @Test
    @DisplayName("[API][DELETE] 존재하지 않는 브랜드 이름으로 삭제 요청 시 실패")
    void deleteBrandWithNonExistingNameTest() throws Exception {
        // Given
        String nonExistingBrandName = "존재하지않는브랜드";
        ShopRequest request = new ShopRequest();
        request.setBrandName(nonExistingBrandName);

        doThrow(new GeneralApiException(StatusCode.BRAND_NAME_NOT_FOUND))
                .when(shopService)
                .deleteBrand(nonExistingBrandName);

        // When & Then
        mockMvc.perform(delete("/admin/api/brand")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("BRAND_NAME_NOT_FOUND"));

        then(shopService).should().deleteBrand(nonExistingBrandName);
    }

    @Test
    @DisplayName("[API][PATCH] 브랜드 이름 수정 성공")
    void updateBrandNameSuccessTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setBrandName("기존브랜드");
        request.setNewName("새브랜드");

        // When & Then
        mockMvc.perform(patch("/admin/api/brand")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        then(shopService).should().updateBrandName(request.getBrandName(), request.getNewName());
    }

    @Test
    @DisplayName("[API][PATCH] 존재하지 않는 브랜드 이름으로 수정 요청 시 실패")
    void updateBrandNameWithNonExistingNameTest() throws Exception {
        // Given
        String nonExistingBrandName = "존재하지않는브랜드";
        ShopRequest request = new ShopRequest();
        request.setBrandName(nonExistingBrandName);
        request.setNewName("새브랜드");

        doThrow(new GeneralApiException(StatusCode.BRAND_NAME_NOT_FOUND))
                .when(shopService)
                .updateBrandName(nonExistingBrandName, request.getNewName());

        // When & Then
        mockMvc.perform(patch("/admin/api/brand")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("BRAND_NAME_NOT_FOUND"));

        then(shopService).should().updateBrandName(nonExistingBrandName, request.getNewName());
    }

    @Test
    @DisplayName("[API][POST] 카테고리 등록 성공")
    void createCategorySuccessTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setCategoryName("새카테고리");

        // When & Then
        mockMvc.perform(post("/admin/api/category")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        then(shopService).should().createCategory(request.getCategoryName());
    }

    @Test
    @DisplayName("[API][POST] 이미 존재하는 카테고리 이름으로 등록 요청 시 실패")
    void createCategoryWithExistingNameTest() throws Exception {
        // Given
        String existingCategoryName = "기존카테고리";
        ShopRequest request = new ShopRequest();
        request.setCategoryName(existingCategoryName);

        doThrow(new GeneralApiException(StatusCode.CATEGORY_NAME_ALREADY_EXISTS))
                .when(shopService)
                .createCategory(existingCategoryName);

        // When & Then
        mockMvc.perform(post("/admin/api/category")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("CATEGORY_NAME_ALREADY_EXISTS"));

        then(shopService).should().createCategory(existingCategoryName);
    }

    @Test
    @DisplayName("[API][DELETE] 카테고리 삭제 성공")
    void deleteCategorySuccessTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setCategoryName("삭제할카테고리");

        // When & Then
        mockMvc.perform(delete("/admin/api/category")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        then(shopService).should().deleteCategory(request.getCategoryName());
    }

    @Test
    @DisplayName("[API][DELETE] 존재하지 않는 카테고리 이름으로 삭제 요청 시 실패")
    void deleteCategoryWithNonExistingNameTest() throws Exception {
        // Given
        String nonExistingCategoryName = "존재하지않는카테고리";
        ShopRequest request = new ShopRequest();
        request.setCategoryName(nonExistingCategoryName);

        doThrow(new GeneralApiException(StatusCode.CATEGORY_NAME_NOT_FOUND))
                .when(shopService)
                .deleteCategory(nonExistingCategoryName);

        // When & Then
        mockMvc.perform(delete("/admin/api/category")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("CATEGORY_NAME_NOT_FOUND"));

        then(shopService).should().deleteCategory(nonExistingCategoryName);
    }

    @Test
    @DisplayName("[API][PATCH] 카테고리 이름 수정 성공")
    void updateCategoryNameSuccessTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setCategoryName("기존카테고리");
        request.setNewName("새카테고리");

        // When & Then
        mockMvc.perform(patch("/admin/api/category")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        then(shopService).should().updateCategoryName(request.getCategoryName(), request.getNewName());
    }

    @Test
    @DisplayName("[API][PATCH] 존재하지 않는 카테고리 이름으로 수정 요청 시 실패")
    void updateCategoryNameWithNonExistingNameTest() throws Exception {
        // Given
        String nonExistingCategoryName = "존재하지않는카테고리";
        String newName = "새카테고리";
        ShopRequest request = new ShopRequest();
        request.setCategoryName(nonExistingCategoryName);
        request.setNewName(newName);

        doThrow(new GeneralApiException(StatusCode.CATEGORY_NAME_NOT_FOUND))
                .when(shopService)
                .updateCategoryName(nonExistingCategoryName, newName);

        // When & Then
        mockMvc.perform(patch("/admin/api/category")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("CATEGORY_NAME_NOT_FOUND"));

        then(shopService).should().updateCategoryName(nonExistingCategoryName, newName);
    }

    @Test
    @DisplayName("[API][POST] 아이템 등록 성공")
    void createSkuItemSuccessTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setBrandName("BrandName");
        request.setCategoryName("CategoryName");
        request.setPrice(new BigDecimal("10000"));
        SkuDto skuDto = new SkuDto("1");

        given(shopService.createSkuItem(request.getBrandName(), request.getCategoryName(), request.getPrice()))
                .willReturn(skuDto);

        // When & Then
        mockMvc.perform(post("/admin/api/item")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("1"));
    }


    @Test
    @DisplayName("[API][POST] 아이템 등록 실패 - 브랜드 이름을 찾을 수 없음")
    void createSkuItemBrandNotFoundTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setBrandName("NonExistingBrand");
        request.setCategoryName("CategoryName");
        request.setPrice(new BigDecimal("10000"));

        given(shopService.createSkuItem(request.getBrandName(), request.getCategoryName(), request.getPrice()))
                .willThrow(new GeneralApiException(StatusCode.BRAND_NAME_NOT_FOUND));

        // When & Then
        mockMvc.perform(post("/admin/api/item")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("BRAND_NAME_NOT_FOUND"));
    }

    @Test
    @DisplayName("[API][POST] 아이템 등록 실패 - 카테고리 이름을 찾을 수 없음")
    void createSkuItemCategoryNotFoundTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setBrandName("BrandName");
        request.setCategoryName("NonExistingCategory");
        request.setPrice(new BigDecimal("10000"));

        given(shopService.createSkuItem(request.getBrandName(), request.getCategoryName(), request.getPrice()))
                .willThrow(new GeneralApiException(StatusCode.CATEGORY_NAME_NOT_FOUND));

        // When & Then
        mockMvc.perform(post("/admin/api/item")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("CATEGORY_NAME_NOT_FOUND"));
    }

    @Test
    @DisplayName("[API][PUT] 아이템 전체 수정 성공")
    void updateItemByIdSuccessTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setId("1");
        request.setBrandName("BrandName");
        request.setCategoryName("CategoryName");
        request.setPrice(new BigDecimal("10000"));

        // When & Then
        mockMvc.perform(put("/admin/api/item")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[API][PUT] 아이템 전체 수정 실패 - 브랜드 이름을 찾을 수 없음")
    void updateItemByIdBrandNotFoundTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setId("1");
        request.setBrandName("NonExistingBrand");
        request.setCategoryName("CategoryName");
        request.setPrice(new BigDecimal("10000"));

        doThrow(new GeneralApiException(StatusCode.BRAND_NAME_NOT_FOUND))
                .when(shopService).updateSkuItemById(request.getId(), request.getBrandName(), request.getCategoryName(), request.getPrice());

        // When & Then
        mockMvc.perform(put("/admin/api/item")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("BRAND_NAME_NOT_FOUND"));
    }

    @Test
    @DisplayName("[API][PUT] 아이템 전체 수정 실패 - 카테고리 이름을 찾을 수 없음")
    void updateItemByIdCategoryNotFoundTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setId("1");
        request.setBrandName("BrandName");
        request.setCategoryName("NonExistingCategory");
        request.setPrice(new BigDecimal("10000"));

        doThrow(new GeneralApiException(StatusCode.CATEGORY_NAME_NOT_FOUND))
                .when(shopService).updateSkuItemById(request.getId(), request.getBrandName(), request.getCategoryName(), request.getPrice());

        // When & Then
        mockMvc.perform(put("/admin/api/item")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("CATEGORY_NAME_NOT_FOUND"));
    }

    @Test
    @DisplayName("[API][PUT] 아이템 전체 수정 실패 - 아이템을 찾을 수 없음")
    void updateItemByIdNotFoundTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setId("NonExistingId");
        request.setBrandName("BrandName");
        request.setCategoryName("CategoryName");
        request.setPrice(new BigDecimal("10000"));

        doThrow(new GeneralApiException(StatusCode.ITEM_NOT_FOUND))
                .when(shopService).updateSkuItemById(request.getId(), request.getBrandName(), request.getCategoryName(), request.getPrice());

        // When & Then
        mockMvc.perform(put("/admin/api/item")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("ITEM_NOT_FOUND"));
    }

    @Test
    @DisplayName("[API][DELETE] 아이템 삭제 성공")
    void deleteItemByIdSuccessTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setId("1");

        // When & Then
        mockMvc.perform(delete("/admin/api/item")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[API][DELETE] 아이템 삭제 실패 - 아이템을 찾을 수 없음")
    void deleteItemByIdNotFoundTest() throws Exception {
        // Given
        ShopRequest request = new ShopRequest();
        request.setId("NonExistingId");

        doThrow(new GeneralApiException(StatusCode.ITEM_NOT_FOUND))
                .when(shopService).deleteSkuItemById(request.getId());

        // When & Then
        mockMvc.perform(delete("/admin/api/item")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.statusCode").value("ITEM_NOT_FOUND"));
    }
}