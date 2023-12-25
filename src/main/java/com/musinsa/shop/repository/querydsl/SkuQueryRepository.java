package com.musinsa.shop.repository.querydsl;

import com.musinsa.shop.domain.QBrand;
import com.musinsa.shop.domain.QCategory;
import com.musinsa.shop.domain.QSku;
import com.musinsa.shop.dto.BrandDto;
import com.musinsa.shop.dto.SkuDto;
import com.querydsl.core.types.Projections;

import java.util.List;

public interface SkuQueryRepository {
    /**
     * Sku 에서 각 카테고리 가격의 합이 최저인 브랜드 조회.
     * @return BrandDto 브랜드 아이디, 브랜드명 포함
     */
    BrandDto getLowestTotalPriceBrand();

    /**
     * Sku 에서 브랜드 ID 에 해당하는 카테고리와 가격 조회.
     * @param brandId String 브랜드 아이디
     * @return List<SkuDto> 카테고리명, 가격을 포함
     */
    List<SkuDto> getItemsByBrandId(String brandId);


    /**
     * Sku 에서 카테고리명에 해당하는 최저가격인 브랜드와 가격 조회.
     * @param categoryName String 카테고리명
     * @return SkuDto 브랜드명, 가격을 포함
     */
    SkuDto getLowestPriceItemByCategoryName(String categoryName);

    /**
     * Sku 에서 카테고리명에 해당하는 최저가격인 브랜드와 가격 조회.
     * @param categoryName String 카테고리명
     * @return SkuDto 브랜드명, 가격을 포함
     */
    SkuDto getHighestPriceItemByCategoryName(String categoryName);

    List<SkuDto> getAllItems();
}
