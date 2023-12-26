package com.musinsa.shop.repository;

import com.musinsa.shop.config.JpaConfig;
import com.musinsa.shop.domain.Category;
import com.musinsa.shop.dto.CategoryDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("DB Test - 카테고리")
@DataJpaTest
@Import(JpaConfig.class)
class CategoryRepositoryTest {

    private final CategoryRepository categoryRepository;

    CategoryRepositoryTest(@Autowired CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @DisplayName("전체 카테고리 리스트 조회")
    @Test
    void getCategoryListTest(){
        // When
        List<CategoryDto> test = categoryRepository.getCategoryList();
        // Then
        assertEquals(test.size(), 8);
    }

    @DisplayName("카테고리명으로 카테고리 조회")
    @Test
    void findByNameTest(){
        // Given
        String categoryName = "상의";

        // When
        Optional<Category> result = categoryRepository.findByName(categoryName);

        // Then
        assertTrue(result.isPresent());
        assertEquals("상의", result.get().getName());
    }
}