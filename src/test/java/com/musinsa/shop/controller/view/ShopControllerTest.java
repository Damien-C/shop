package com.musinsa.shop.controller.view;

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

@DisplayName("[VIEW] 컨트롤러 - 메인")
@WebMvcTest(ShopController.class)
class ShopControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @Test
    @DisplayName("루트 경로 요청 시 메인 페이지로 리다이렉트")
    void rootTest() throws Exception {
        // Given
        List<CategoryDto> categoryList = Arrays.asList(
                new CategoryDto("1", "Category 1"),
                new CategoryDto("2", "Category 2")
        );
        given(shopService.getCategoryList()).willReturn(categoryList);

        // When & Then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("view/main"))
                .andExpect(model().attributeExists("categoryList"))
                .andExpect(model().attribute("categoryList", hasSize(2)))
                .andExpect(model().attribute("categoryList", hasItems(
                        hasProperty("name", is("Category 1")),
                        hasProperty("name", is("Category 2"))
                )));
    }
}