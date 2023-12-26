package com.musinsa.shop.repository;

import com.musinsa.shop.config.JpaConfig;
import com.musinsa.shop.dto.BrandDto;
import com.musinsa.shop.dto.SkuDto;
import com.musinsa.shop.dto.SkuViewInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("DB Test - 아이템")
@DataJpaTest
@Import(JpaConfig.class)
class SkuRepositoryTest {

    private final SkuRepository skuRepository;

    public SkuRepositoryTest(@Autowired SkuRepository skuRepository) {
        this.skuRepository = skuRepository;
    }

    @DisplayName("Sku 에서 각 카테고리 가격의 합이 최저인 브랜드 조회")
    @Test
    void getLowestTotalPriceBrandTest(){

        // When
        BrandDto brand = skuRepository.getLowestTotalPriceBrand();

        //Then
        assertEquals(brand.getId(), "4");
        assertEquals(brand.getName(), "D");
    }

    @DisplayName("Sku 에서 브랜드 ID 에 해당하는 카테고리와 가격 조회")
    @Test
    void getItemsByBrandIdTest(){
        // given
        String brandId = "4";

        // When
        List<SkuDto> skuList = skuRepository.getItemsByBrandId(brandId);

        //Then
        assertEquals(skuList.size(), 8);
    }

    @DisplayName("Sku 에서 카테고리명에 해당하는 최저가격인 브랜드와 가격 조회")
    @Test
    void getLowestPriceItemByCategoryNameTest(){
        // Given
        String categoryName = "상의";

        // When
        SkuDto sku = skuRepository.getLowestPriceItemByCategoryName(categoryName);

        // Then
        assertEquals(sku.getBrand(), "C");
        assertEquals(0, sku.getPrice().compareTo(new BigDecimal("10000")));
    }

    @DisplayName("Sku 에서 카테고리명에 해당하는 최고가격인 브랜드와 가격 조회")
    @Test
    void getHighestPriceItemByCategoryNameTest(){
        // Given
        String categoryName = "상의";

        // When
        SkuDto sku = skuRepository.getHighestPriceItemByCategoryName(categoryName);

        // Then
        assertEquals(sku.getBrand(), "I");
        assertEquals(0, sku.getPrice().compareTo(new BigDecimal("11400")));
    }

    @DisplayName("전체 상품 조회")
    @Test
    void getAllItemsTest(){

        // When
        List<SkuDto> skuList = skuRepository.getAllItems();

        // Then
        assertEquals(skuList.size(), 72);
    }

    @DisplayName("카테고리 별 최저가격 브랜드와 상품 가격, 총액 조회")
    @Test
    void getLowestPriceItemsTest(){

        // When
        List<SkuViewInterface> skuList = skuRepository.getLowestPriceItems();

        // Then
        List<SkuViewInterface> mockData = Arrays.asList(
                new SkuView("C", "상의", new BigDecimal("10000")),
                new SkuView("E", "아우터", new BigDecimal("5000")),
                new SkuView("D", "바지", new BigDecimal("3000")),
                new SkuView("G", "스니커즈", new BigDecimal("9000")),
                new SkuView("A", "가방", new BigDecimal("2000")),
                new SkuView("D", "모자", new BigDecimal("1500")),
                new SkuView("I", "양말", new BigDecimal("1700")),
                new SkuView("F", "액세서리", new BigDecimal("1900"))
        );

        assertEquals(8, skuList.size());
        for (int i = 0; i < skuList.size(); i++) {
            assertEquals(mockData.get(i).getBrand(), skuList.get(i).getBrand());
            assertEquals(mockData.get(i).getCategory(), skuList.get(i).getCategory());
            assertEquals(mockData.get(i).getPrice(), skuList.get(i).getPrice());
        }
    }




    private static class SkuView implements SkuViewInterface {
        private final String brand;
        private final String category;
        private final BigDecimal price;

        public SkuView(String brand, String category, BigDecimal price) {
            this.brand = brand;
            this.category = category;
            this.price = price;
        }

        @Override
        public String getBrand() {
            return this.brand;
        }

        @Override
        public String getCategory() {
            return this.category;
        }

        @Override
        public BigDecimal getPrice() {
            return this.price;
        }
    }
}