package com.musinsa.shop.service;

import com.musinsa.shop.domain.Brand;
import com.musinsa.shop.domain.Category;
import com.musinsa.shop.domain.Sku;
import com.musinsa.shop.dto.*;
import com.musinsa.shop.exception.GeneralApiException;
import com.musinsa.shop.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("Business Logic Test")
@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @InjectMocks
    private ShopService sut;

    @Mock private BrandRepository brandRepository;
    @Mock private BrandQueryRepository brandQueryRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private CategoryQueryRepository categoryQueryRepository;
    @Mock private SkuRepository skuRepository;
    @Mock private SkuQueryRepository skuQueryRepository;


    @DisplayName("조회된 아이템 리스트를 응답객체에 저장하고 아이템 리스트를 순회하여 가격 합산")
    @Test
    void getLowestPriceItemsTest() {
        // Given
        given(skuRepository.getLowestPriceItems()).willReturn(List.of(
                createSkuViewInterface("A", "상의", "10000"),
                createSkuViewInterface("B", "하의", "20000")
        ));

        // When
        ShopResponse result = sut.getLowestPriceItems();

        // Then
        assertEquals(result.getLowestPriceList().size(), 2);
        assertEquals(new BigDecimal("30000"), result.getTotalPrice());
        then(skuRepository).should().getLowestPriceItems();
    }


    @DisplayName("조회된 브랜드와 아이템 리스트를 응답객체에 저장하고 아이템 리스트를 순회하여 가격 합산")
    @Test
    void getLowestPriceBrandItemsTest() {
        // Given
        BrandDto brand = new BrandDto("1", "A");
        given(skuQueryRepository.getLowestTotalPriceBrand())
                .willReturn(brand);
        given(skuQueryRepository.getItemsByBrandId(brand.getId()))
                .willReturn(List.of(
                        createSkuDto("1", "A", "상의", "10000"),
                        createSkuDto("1", "A", "하의", "20000")
                ));

        // When
        ShopResponse result = sut.getLowestPriceBrandItems();

        // Then
        assertEquals(result.getLowestPrice().getBrand(), "A");
        assertEquals(result.getLowestPrice().getCategory().size(), 2);
        assertEquals(new BigDecimal("30000"), result.getLowestPrice().getTotalPrice());
        then(skuQueryRepository).should().getLowestTotalPriceBrand();
        then(skuQueryRepository).should().getItemsByBrandId(brand.getId());
    }

    @DisplayName("조회된 두개의 아이템들을 각각 리스트로 응답객체에 저장하고 검색값을 그대로 응답객체에 저장")
    @Test
    void getLowestAndHighestPriceItemsTest() {
        // Given
        String categoryName = "상의";
        SkuDto lowestSku = createSkuDto("1", "A", "상의", "10000");
        SkuDto highestSku = createSkuDto("2", "B", "상의", "20000");
        given(skuQueryRepository.getLowestPriceItemByCategoryName(categoryName))
                .willReturn(lowestSku);
        given(skuQueryRepository.getHighestPriceItemByCategoryName(categoryName))
                .willReturn(highestSku);

        // When
        ShopResponse result = sut.getLowestAndHighestPriceItems(categoryName);

        // Then
        assertEquals(result.getLowestPriceList().get(0), lowestSku);
        assertEquals(result.getHighestPriceList().get(0), highestSku);
        assertEquals(result.getCategory(), categoryName);
        then(skuQueryRepository).should().getLowestPriceItemByCategoryName(categoryName);
        then(skuQueryRepository).should().getHighestPriceItemByCategoryName(categoryName);
    }

    @DisplayName("조회된 아이템 리스트를 그대로 리턴")
    @Test
    void getAllSkuItemsTest() {
        // Given
        SkuDto sku1 = createSkuDto("1", "A", "상의", "10000");
        SkuDto sku2 = createSkuDto("2", "B", "상의", "20000");
        given(skuQueryRepository.getAllItems())
                .willReturn(List.of(sku1, sku2));
        // When
        List<SkuDto> result = sut.getAllSkuItems();

        // Then
        assertEquals(result.size(), 2);
        then(skuQueryRepository).should().getAllItems();
    }

    @DisplayName("브랜드 이름을 매개변수로 새로운 브랜드 생성")
    @Test
    public void createBrandTest() {
        // Given
        String newBrandName = "NewBrand";
        given(brandRepository.findByName(newBrandName)).willReturn(Optional.empty());

        // When
        sut.createBrand(newBrandName);

        // Then
        then(brandRepository).should().save(any(Brand.class));
    }

    @DisplayName("브랜드 이름이 중복될 경우 예외처리")
    @Test
    public void createBrandExceptionTest() {
        // Given
        String existingBrandName = "ExistingBrand";
        given(brandRepository.findByName(existingBrandName)).willReturn(Optional.of(new Brand(existingBrandName)));

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.createBrand(existingBrandName);
        });
    }

    @DisplayName("브랜드 삭제시 이름이 존재하지 않을경우 예외처리")
    @Test
    public void deleteBrandExceptionTest() {
        // Given
        String nonExistingBrandName = "NonExistingBrand";
        given(brandRepository.findByName(nonExistingBrandName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.deleteBrand(nonExistingBrandName);
        });
    }

    @DisplayName("브랜드 이름을 매개변수로 브랜드 삭제")
    @Test
    public void deleteBrandTest() {
        // Given
        String existingBrandName = "ExistingBrand";
        Brand existingBrand = new Brand(existingBrandName);
        given(brandRepository.findByName(existingBrandName)).willReturn(Optional.of(existingBrand));

        // When
        sut.deleteBrand(existingBrandName);

        // Then
        then(brandRepository).should().delete(existingBrand);
    }

    @DisplayName("브랜드 이름 수정시 이름이 존재하지 않을경우 예외처리")
    @Test
    public void updateBrandNameExceptionTest() {
        // Given
        String nonExistingBrandName = "NonExistingBrand";
        given(brandRepository.findByName(nonExistingBrandName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.updateBrandName(nonExistingBrandName, "NewName");
        });
    }

    @DisplayName("브랜드 이름을 매개변수로 브랜드 이름 수정")
    @Test
    public void updateBrandNameTest() {
        // Given
        String existingBrandName = "ExistingBrand";
        Brand existingBrand = new Brand(existingBrandName);
        given(brandRepository.findByName(existingBrandName)).willReturn(Optional.of(existingBrand));

        // When
        sut.updateBrandName(existingBrandName, "NewName");

        // Then
        assertEquals("NewName", existingBrand.getName());
        then(brandRepository).should().save(existingBrand);
    }

    @DisplayName("새로운 카테고리 생성")
    @Test
    public void createCategoryTest() {
        // Given
        String newCategoryName = "NewCategory";
        given(categoryRepository.findByName(newCategoryName)).willReturn(Optional.empty());

        // When
        sut.createCategory(newCategoryName);

        // Then
        then(categoryRepository).should().save(any(Category.class));
    }

    @DisplayName("카테고리 이름이 중복될 경우 예외처리")
    @Test
    public void createCategoryExceptionTest() {
        // Given
        String existingCategoryName = "ExistingCategory";
        given(categoryRepository.findByName(existingCategoryName)).willReturn(Optional.of(new Category(existingCategoryName)));

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.createCategory(existingCategoryName);
        });
    }

    @DisplayName("카테고리 삭제시 이름이 존재하지 않을 경우 예외처리")
    @Test
    public void deleteCategoryExceptionTest() {
        // Given
        String nonExistingCategoryName = "NonExistingCategory";
        given(categoryRepository.findByName(nonExistingCategoryName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.deleteCategory(nonExistingCategoryName);
        });
    }

    @DisplayName("카테고리 이름을 매개변수로 카테고리 삭제")
    @Test
    public void deleteCategoryTest() {
        // Given
        String existingCategoryName = "ExistingCategory";
        Category existingCategory = new Category(existingCategoryName);
        given(categoryRepository.findByName(existingCategoryName)).willReturn(Optional.of(existingCategory));

        // When
        sut.deleteCategory(existingCategoryName);

        // Then
        then(categoryRepository).should().delete(existingCategory);
    }

    @DisplayName("카테고리 이름 수정시 이름이 존재하지 않을 경우 예외처리")
    @Test
    public void updateCategoryNameExceptionTest() {
        // Given
        String nonExistingCategoryName = "NonExistingCategory";
        given(categoryRepository.findByName(nonExistingCategoryName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.updateCategoryName(nonExistingCategoryName, "NewName");
        });
    }

    @DisplayName("카테고리 이름을 매개변수로 카테고리 이름 수정")
    @Test
    public void updateCategoryNameTest() {
        // Given
        String existingCategoryName = "ExistingCategory";
        Category existingCategory = new Category(existingCategoryName);
        given(categoryRepository.findByName(existingCategoryName)).willReturn(Optional.of(existingCategory));

        // When
        sut.updateCategoryName(existingCategoryName, "NewName");

        // Then
        assertEquals("NewName", existingCategory.getName());
        then(categoryRepository).should().save(existingCategory);
    }

    @DisplayName("새로운 아이템 생성")
    @Test
    public void createSkuItemTest() {
        // Given
        String brandName = "BrandName";
        String categoryName = "CategoryName";
        BigDecimal price = new BigDecimal("100");
        Brand brand = new Brand(brandName);
        Category category = new Category(categoryName);
        given(brandRepository.findByName(brandName)).willReturn(Optional.of(brand));
        given(categoryRepository.findByName(categoryName)).willReturn(Optional.of(category));
        given(skuRepository.save(any(Sku.class))).willAnswer(invocation -> invocation.getArgument(0));

        // When
        SkuDto result = sut.createSkuItem(brandName, categoryName, price);

        // Then
        assertNotNull(result);
        then(skuRepository).should().save(any(Sku.class));
    }

    @DisplayName("브랜드가 존재하지 않을 때 아이템 생성 실패")
    @Test
    public void createSkuItemWhenBrandNotFoundTest() {
        // Given
        String brandName = "NonExistingBrand";
        String categoryName = "CategoryName";
        BigDecimal price = new BigDecimal("100");
        given(brandRepository.findByName(brandName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.createSkuItem(brandName, categoryName, price);
        });
    }

    @DisplayName("카테고리가 존재하지 않을 때 아이템 생성 실패")
    @Test
    public void createSkuItemWhenCategoryNotFoundTest() {
        // Given
        String brandName = "BrandName";
        String categoryName = "NonExistingCategory";
        BigDecimal price = new BigDecimal("100");
        Brand brand = new Brand(brandName);
        given(brandRepository.findByName(brandName)).willReturn(Optional.of(brand));
        given(categoryRepository.findByName(categoryName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.createSkuItem(brandName, categoryName, price);
        });
    }

    @DisplayName("브랜드와 카테고리가 존재할 때 아이템 삭제")
    @Test
    public void deleteSkuItemByBrandNameAndCategoryTest() {
        // Given
        String brandName = "BrandName";
        String categoryName = "CategoryName";
        Brand brand = new Brand(brandName);
        Category category = new Category(categoryName);
        given(brandRepository.findByName(brandName)).willReturn(Optional.of(brand));
        given(categoryRepository.findByName(categoryName)).willReturn(Optional.of(category));

        // When
        sut.deleteSkuItemByBrandNameAndCategory(brandName, categoryName);

        // Then
        then(skuRepository).should().deleteByBrandAndCategory(brand, category);
    }

    @DisplayName("브랜드가 존재하지 않을 때 아이템 삭제 실패")
    @Test
    public void deleteSkuItemByBrandNameAndCategoryWhenBrandNotFoundTest() {
        // Given
        String nonExistingBrandName = "NonExistingBrand";
        String categoryName = "CategoryName";
        given(brandRepository.findByName(nonExistingBrandName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.deleteSkuItemByBrandNameAndCategory(nonExistingBrandName, categoryName);
        });
    }

    @DisplayName("카테고리가 존재하지 않을 때 아이템 삭제 실패")
    @Test
    public void deleteSkuItemByBrandNameAndCategoryWhenCategoryNotFoundTest() {
        // Given
        String brandName = "BrandName";
        String nonExistingCategoryName = "NonExistingCategory";
        Brand brand = new Brand(brandName);
        given(brandRepository.findByName(brandName)).willReturn(Optional.of(brand));
        given(categoryRepository.findByName(nonExistingCategoryName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.deleteSkuItemByBrandNameAndCategory(brandName, nonExistingCategoryName);
        });
    }

    @DisplayName("브랜드가 존재하지 않을 때 아이템 가격 수정 실패")
    @Test
    public void updateSkuItemPriceWhenBrandNotFoundTest() {
        // Given
        String nonExistingBrandName = "NonExistingBrand";
        String categoryName = "CategoryName";
        BigDecimal newPrice = new BigDecimal("200");
        given(brandRepository.findByName(nonExistingBrandName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.updateSkuItemPriceByBrandNameAndCategory(nonExistingBrandName, categoryName, newPrice);
        });
    }

    @DisplayName("카테고리가 존재하지 않을 때 아이템 가격 수정 실패")
    @Test
    public void updateSkuItemPriceWhenCategoryNotFoundTest() {
        // Given
        String brandName = "BrandName";
        String nonExistingCategoryName = "NonExistingCategory";
        BigDecimal newPrice = new BigDecimal("200");
        Brand brand = new Brand(brandName);
        given(brandRepository.findByName(brandName)).willReturn(Optional.of(brand));
        given(categoryRepository.findByName(nonExistingCategoryName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.updateSkuItemPriceByBrandNameAndCategory(brandName, nonExistingCategoryName, newPrice);
        });
    }

    @DisplayName("해당 브랜드와 카테고리에 아이템이 존재하지 않을 때 실패")
    @Test
    public void updateSkuItemPriceWhenItemNotFoundTest() {
        // Given
        String brandName = "BrandName";
        String categoryName = "CategoryName";
        BigDecimal newPrice = new BigDecimal("200");
        Brand brand = new Brand(brandName);
        Category category = new Category(categoryName);
        given(brandRepository.findByName(brandName)).willReturn(Optional.of(brand));
        given(categoryRepository.findByName(categoryName)).willReturn(Optional.of(category));
        given(skuRepository.findByBrandAndCategory(brand, category)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.updateSkuItemPriceByBrandNameAndCategory(brandName, categoryName, newPrice);
        });
    }

    @DisplayName("브랜드가 존재하지 않을 때 아이템 업데이트 실패")
    @Test
    public void updateSkuItemByIdWhenBrandNotFoundTest() {
        // Given
        String id = "skuId";
        String nonExistingBrandName = "NonExistingBrand";
        String categoryName = "CategoryName";
        BigDecimal price = new BigDecimal("200");
        given(brandRepository.findByName(nonExistingBrandName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.updateSkuItemById(id, nonExistingBrandName, categoryName, price);
        });
    }

    @DisplayName("카테고리가 존재하지 않을 때 아이템 업데이트 실패")
    @Test
    public void updateSkuItemByIdWhenCategoryNotFoundTest() {
        // Given
        String id = "skuId";
        String brandName = "BrandName";
        String nonExistingCategoryName = "NonExistingCategory";
        BigDecimal price = new BigDecimal("200");
        Brand brand = new Brand(brandName);
        given(brandRepository.findByName(brandName)).willReturn(Optional.of(brand));
        given(categoryRepository.findByName(nonExistingCategoryName)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.updateSkuItemById(id, brandName, nonExistingCategoryName, price);
        });
    }

    @DisplayName("해당 아이템이 존재하지 않을 때 업데이트 실패")
    @Test
    public void updateSkuItemByIdWhenItemNotFoundTest() {
        // Given
        String nonExistingId = "nonExistingSkuId";
        String brandName = "BrandName";
        String categoryName = "CategoryName";
        BigDecimal price = new BigDecimal("200");
        Brand brand = new Brand(brandName);
        Category category = new Category(categoryName);
        given(brandRepository.findByName(brandName)).willReturn(Optional.of(brand));
        given(categoryRepository.findByName(categoryName)).willReturn(Optional.of(category));
        given(skuRepository.findById(nonExistingId)).willReturn(Optional.empty());

        // When & Then
        assertThrows(GeneralApiException.class, () -> {
            sut.updateSkuItemById(nonExistingId, brandName, categoryName, price);
        });
    }

    @DisplayName("브랜드, 카테고리, 아이템이 모두 존재할 때 아이템 업데이트")
    @Test
    public void updateSkuItemByIdTest() {
        // Given
        String id = "skuId";
        String brandName = "BrandName";
        String categoryName = "CategoryName";
        BigDecimal newPrice = new BigDecimal("200");
        Brand brand = new Brand(brandName);
        Category category = new Category(categoryName);
        Sku sku = new Sku(new BigDecimal("100"), brand, category);
        given(brandRepository.findByName(brandName)).willReturn(Optional.of(brand));
        given(categoryRepository.findByName(categoryName)).willReturn(Optional.of(category));
        given(skuRepository.findById(id)).willReturn(Optional.of(sku));

        // When
        sut.updateSkuItemById(id, brandName, categoryName, newPrice);

        // Then
        assertEquals(newPrice, sku.getPrice());
        assertEquals(brand, sku.getBrand());
        assertEquals(category, sku.getCategory());
        then(skuRepository).should().save(sku);
    }

    @DisplayName("아이템 ID로 삭제")
    @Test
    public void deleteSkuItemByIdTest() {
        // Given
        String id = "skuId";

        // When
        sut.deleteSkuItemById(id);

        // Then
        then(skuRepository).should().deleteById(id);
    }

    @DisplayName("카테고리 리스트 조회")
    @Test
    public void getCategoryListTest() {
        // Given
        List<CategoryDto> categoryList = Arrays.asList(
                new CategoryDto("Category1"),
                new CategoryDto("Category2")
        );
        given(categoryQueryRepository.getCategoryList()).willReturn(categoryList);

        // When
        List<CategoryDto> result = sut.getCategoryList();

        // Then
        assertEquals(categoryList.size(), result.size());
        assertEquals(categoryList.get(0).getName(), result.get(0).getName());
        assertEquals(categoryList.get(1).getName(), result.get(1).getName());
    }

    @DisplayName("브랜드 리스트 조회")
    @Test
    public void getBrandListTest() {
        // Given
        List<BrandDto> brandList = Arrays.asList(
                new BrandDto("1", "Brand1"),
                new BrandDto("2", "Brand2")
        );
        given(brandQueryRepository.getBrandList()).willReturn(brandList);

        // When
        List<BrandDto> result = sut.getBrandList();

        // Then
        assertEquals(brandList.size(), result.size());
        assertEquals(brandList.get(0).getName(), result.get(0).getName());
        assertEquals(brandList.get(1).getName(), result.get(1).getName());
    }

    private SkuDto createSkuDto(String id, String brand, String category, String price){
        return new SkuDto(id, brand, category, new BigDecimal(price));
    }


    private SkuViewInterface createSkuViewInterface(String brand, String category, String price){
        return new SkuViewInterface() {
            @Override
            public String getBrand() {
                return brand;
            }
            @Override
            public String getCategory() {
                return category;
            }
            @Override
            public BigDecimal getPrice() {
                return new BigDecimal(price);
            }
        };
    }

}