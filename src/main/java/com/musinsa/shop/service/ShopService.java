package com.musinsa.shop.service;

import com.musinsa.shop.constant.StatusCode;
import com.musinsa.shop.domain.Brand;
import com.musinsa.shop.domain.Category;
import com.musinsa.shop.domain.Sku;
import com.musinsa.shop.dto.*;
import com.musinsa.shop.exception.GeneralApiException;
import com.musinsa.shop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ShopService {

    private final SkuRepository skuRepository;
    private final SkuQueryRepository skuQueryRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    /**
     * 구현 1) 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회
     * @return ShopResponse BigDecimal 총액, List<SkuViewInterface> 상품리스트
     */
    @Transactional(readOnly = true)
    public ShopResponse getLowestPriceItems(){

        // 리포지토리에서 카테고리 별 최저가격 브랜드를 조회하고 리스트 객체에 저장
        List<SkuViewInterface> skuList = skuRepository.getLowestPriceItems();

        // 리스트를 순회하며 총액을 계산
        BigDecimal totalPrice = skuList.stream()
                .map(SkuViewInterface::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 응답 객체 생성 및 결과값 설정 후 리턴
        return ShopResponse.builder()
                .totalPrice(totalPrice)
                .lowestPriceList(skuList)
                .build();
    }

    /**
     * 구현 2) 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회
     *
     * @return ShopResponse PriceDto 최저가
     */
    @Transactional(readOnly = true)
    public ShopResponse getLowestPriceBrandItems(){

        // 각 카테고리 가격의 합이 최저인 브랜드 조회 맟 저장
        BrandDto brand = skuQueryRepository.getLowestTotalPriceBrand();

        // 브랜드 아이디에 해당하는 상품리스트 조회 및 저장
        List<SkuDto> skuList = skuQueryRepository.getItemsByBrandId(brand.getId());

        // 총액 계산
        BigDecimal totalPrice = skuList.stream()
                .map(SkuDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 가격 정보 객체 생성
        PriceDto priceDto = PriceDto.builder()
                .brand(brand.getName())
                .category(skuList)
                .totalPrice(totalPrice)
                .build();

        // 응답 객체 생성 및 결과값 설정 후 리턴
        return ShopResponse.builder()
                .lowestPrice(priceDto)
                .build();
    }

    /**
     * 구현 3) 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회
     * @param categoryName String 카테고리명
     * @return ShopResponse String 카테고리명, List<SkuDto>최저가, List<SkuDto>최고가
     */
    @Transactional(readOnly = true)
    public ShopResponse getLowestAndHighestPriceItems(String categoryName){

        // 최저가 및 최고가 브랜드와 가격 조회
        SkuDto lowestSku = skuQueryRepository.getLowestPriceItemByCategoryName(categoryName);
        SkuDto highestSku = skuQueryRepository.getHighestPriceItemByCategoryName(categoryName);

        // 응답 객체 생성 및 결과 설정 후 리턴
        return ShopResponse.builder()
                .lowestPriceList(Collections.singletonList(lowestSku))
                .highestPriceList(Collections.singletonList(highestSku))
                .category(categoryName)
                .build();
    }


    // --------------------- 관리자 기능 영역 ------------------------- //

    // 전체조회
    @Transactional(readOnly = true)
    public List<SkuDto> getAllSkuItems(){
        return skuQueryRepository.getAllItems();
    }

    // 브랜드 생성
    @Transactional
    public void createBrand(String brandName){
        if(brandRepository.findByName(brandName).isPresent())
            throw new GeneralApiException(StatusCode.BRAND_NAME_ALREADY_EXISTS);

        Brand brand = Brand.builder()
                .name(brandName)
                .build();
        brandRepository.save(brand);
    }
    // 브랜드 삭제
    @Transactional
    public void deleteBrand(String brandName){
        Brand brand = brandRepository.findByName(brandName)
                .orElseThrow(() -> new GeneralApiException(StatusCode.BRAND_NAME_NOT_FOUND));
        brandRepository.delete(brand);
    }

    // 브랜드 이름 수정
    @Transactional
    public void updateBrandName(String brandName, String newName){
        Brand brand = brandRepository.findByName(brandName)
                .orElseThrow(() -> new GeneralApiException(StatusCode.BRAND_NAME_NOT_FOUND));
        brand.setName(newName);
        brandRepository.save(brand);
    }

    // 카테고리 생성
    @Transactional
    public void createCategory(String categoryName) {
        if(categoryRepository.findByName(categoryName).isPresent())
            throw new GeneralApiException(StatusCode.CATEGORY_NAME_ALREADY_EXISTS);

        Category category = Category.builder()
                .name(categoryName)
                .build();
        categoryRepository.save(category);
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new GeneralApiException(StatusCode.CATEGORY_NAME_NOT_FOUND));
        categoryRepository.delete(category);
    }

    // 카테고리 이름 수정
    @Transactional
    public void updateCategoryName(String categoryName, String newName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new GeneralApiException(StatusCode.CATEGORY_NAME_NOT_FOUND));
        category.setName(newName);
        categoryRepository.save(category);
    }

    // 아이템 생성
    @Transactional
    public void createSkuItem(String brandName, String categoryName, BigDecimal price) {

        Brand brand = brandRepository.findByName(brandName)
                .orElseThrow(() -> new GeneralApiException(StatusCode.BRAND_NAME_NOT_FOUND));

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new GeneralApiException(StatusCode.CATEGORY_NAME_NOT_FOUND));

        if(skuRepository.findByBrandAndCategory(brand, category).isEmpty()){
            // 상품 저장
            Sku sku = Sku.builder()
                    .brand(brand)
                    .category(category)
                    .price(price)
                    .build();
            skuRepository.save(sku);
        }
        else throw new GeneralApiException(StatusCode.ITEM_ALREADY_EXISTS);
    }

    // 아이템삭제
    @Transactional
    public void deleteSkuItemByBrandNameAndCategory(String brandName, String categoryName){

        Brand brand = brandRepository.findByName(brandName)
                .orElseThrow(() -> new GeneralApiException(StatusCode.BRAND_NAME_NOT_FOUND));

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new GeneralApiException(StatusCode.CATEGORY_NAME_NOT_FOUND));

        skuRepository.deleteByBrandAndCategory(brand, category);

    }

    // 아이템 가격수정
    @Transactional
    public void updateSkuItemPriceByBrandNameAndCategory(String brandName, String categoryName, BigDecimal price){
        Brand brand = brandRepository.findByName(brandName)
                .orElseThrow(() -> new GeneralApiException(StatusCode.BRAND_NAME_NOT_FOUND));

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new GeneralApiException(StatusCode.CATEGORY_NAME_NOT_FOUND));

        Sku sku = skuRepository.findByBrandAndCategory(brand, category)
                .orElseThrow(() -> new GeneralApiException(StatusCode.ITEM_NOT_FOUND));
        sku.setPrice(price);
        skuRepository.save(sku);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoryList() {
        return categoryQueryRepository.getCategoryList();
    }
}
