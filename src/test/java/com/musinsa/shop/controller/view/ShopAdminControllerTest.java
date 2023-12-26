package com.musinsa.shop.controller.view;

import com.musinsa.shop.dto.BrandDto;
import com.musinsa.shop.dto.CategoryDto;
import com.musinsa.shop.service.ShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("[VIEW] 컨트롤러 - 관리자")
@WebMvcTest(ShopAdminController.class)
class ShopAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @Test
    @DisplayName("어드민 페이지로 리다이렉트 - 브랜드 페이지")
    void adminBrandPageTest() throws Exception {
        // Given
        List<BrandDto> brandList = Arrays.asList(
                new BrandDto("1", "Brand 1"),
                new BrandDto("2", "Brand 2")
        );
        given(shopService.getBrandList()).willReturn(brandList);

        // When & Then
        mockMvc.perform(get("/admin").param("pageType", "brand"))
                .andExpect(status().isOk())
                .andExpect(view().name("view/admin"))
                .andExpect(model().attributeExists("brandList"))
                .andExpect(model().attribute("brandList", hasSize(2)))
                .andExpect(model().attribute("brandList", hasItems(
                        hasProperty("name", is("Brand 1")),
                        hasProperty("name", is("Brand 2"))
                )));
    }

    @Test
    @DisplayName("어드민 페이지로 리다이렉트 - 카테고리 페이지")
    void adminCategoryPageTest() throws Exception {
        // Given
        List<CategoryDto> categoryList = Arrays.asList(
                new CategoryDto("1", "Category 1"),
                new CategoryDto("2", "Category 2")
        );
        given(shopService.getCategoryList()).willReturn(categoryList);

        // When & Then
        mockMvc.perform(get("/admin").param("pageType", "category"))
                .andExpect(status().isOk())
                .andExpect(view().name("view/admin"))
                .andExpect(model().attributeExists("categoryList"))
                .andExpect(model().attribute("categoryList", hasSize(2)))
                .andExpect(model().attribute("categoryList", hasItems(
                        hasProperty("name", is("Category 1")),
                        hasProperty("name", is("Category 2"))
                )));
    }

    @Test
    @DisplayName("어드민 페이지로 리다이렉트 - 아이템 페이지")
    void adminSkuItemPageTest() throws Exception {
        // Given
        List<BrandDto> brandList = Arrays.asList(
                new BrandDto("1", "Brand 1"),
                new BrandDto("2", "Brand 2")
        );
        List<CategoryDto> categoryList = Arrays.asList(
                new CategoryDto("1", "Category 1"),
                new CategoryDto("2", "Category 2")
        );
        given(shopService.getBrandList()).willReturn(brandList);
        given(shopService.getCategoryList()).willReturn(categoryList);

        // When & Then
        mockMvc.perform(get("/admin").param("pageType", "skuItem"))
                .andExpect(status().isOk())
                .andExpect(view().name("view/admin"))
                .andExpect(model().attributeExists("brandList"))
                .andExpect(model().attributeExists("categoryList"))
                .andExpect(model().attribute("brandList", hasSize(2)))
                .andExpect(model().attribute("categoryList", hasSize(2)));
    }

}