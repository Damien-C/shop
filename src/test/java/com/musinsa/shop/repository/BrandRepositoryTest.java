package com.musinsa.shop.repository;

import com.musinsa.shop.config.JpaConfig;
import com.musinsa.shop.domain.Brand;
import com.musinsa.shop.dto.BrandDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DB Test - 브랜드")
@DataJpaTest
@Import(JpaConfig.class)
class BrandRepositoryTest {

    private final BrandRepository brandRepository;

    BrandRepositoryTest(@Autowired BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @DisplayName("전체 브랜드 리스트 조회")
    @Test
    void getBrandListTest(){
        // When
        List<BrandDto> test = brandRepository.getBrandList();
        // Then
        assertEquals(test.size(), 9);
    }

    @DisplayName("브랜드명으로 브랜드 조회")
    @Test
    void findByNameTest(){
        // Given
        String brandName = "A";

        // When
        Optional<Brand> result = brandRepository.findByName(brandName);

        // Then
        assertTrue(result.isPresent());
        assertEquals("A", result.get().getName());
    }
}