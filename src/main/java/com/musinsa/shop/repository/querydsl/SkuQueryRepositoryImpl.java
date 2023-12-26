package com.musinsa.shop.repository.querydsl;

import com.musinsa.shop.domain.QBrand;
import com.musinsa.shop.domain.QCategory;
import com.musinsa.shop.domain.QSku;
import com.musinsa.shop.dto.BrandDto;
import com.musinsa.shop.dto.SkuDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SkuQueryRepositoryImpl implements SkuQueryRepository{

    private final JPAQueryFactory queryFactory;
    /**
     * Sku 에서 각 카테고리 가격의 합이 최저인 브랜드 조회.
     * @return BrandDto 브랜드 아이디, 브랜드명 포함
     */
    public BrandDto getLowestTotalPriceBrand(){

        QSku sku = QSku.sku;
        QBrand brand = QBrand.brand;

        return queryFactory
                .select(Projections.constructor(BrandDto.class,
                        brand.id,
                        brand.name
                ))
                .from(sku)
                .innerJoin(sku.brand, brand)
                .groupBy(sku.brand.id)
                .orderBy(sku.price.sum().asc())
                .fetchFirst();
    }

    /**
     * Sku 에서 브랜드 ID 에 해당하는 카테고리와 가격 조회.
     * @param brandId String 브랜드 아이디
     * @return List<SkuDto> 카테고리명, 가격을 포함
     */
    public List<SkuDto> getItemsByBrandId(String brandId){

        QSku sku = QSku.sku;
        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.fields(SkuDto.class,
                        category.name.as("category"), sku.price))
                .from(sku)
                .innerJoin(sku.category, category)
                .where(sku.brand.id.eq(brandId))
                .fetch();

    }


    /**
     * Sku 에서 카테고리명에 해당하는 최저가격인 브랜드와 가격 조회.
     * @param categoryName String 카테고리명
     * @return SkuDto 브랜드명, 가격을 포함
     */
    public SkuDto getLowestPriceItemByCategoryName(String categoryName){

        QSku sku = QSku.sku;
        QBrand brand = QBrand.brand;
        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.fields(SkuDto.class,
                        brand.name.as("brand"), sku.price))
                .from(sku)
                .innerJoin(sku.brand, brand)
                .innerJoin(sku.category, category)
                .where(category.name.eq(categoryName))
                .orderBy(sku.price.asc())
                .fetchFirst();
    }

    /**
     * Sku 에서 카테고리명에 해당하는 최저가격인 브랜드와 가격 조회.
     * @param categoryName String 카테고리명
     * @return SkuDto 브랜드명, 가격을 포함
     */
    public SkuDto getHighestPriceItemByCategoryName(String categoryName){

        QSku sku = QSku.sku;
        QBrand brand = QBrand.brand;
        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.fields(SkuDto.class,
                        brand.name.as("brand"), sku.price))
                .from(sku)
                .innerJoin(sku.brand, brand)
                .innerJoin(sku.category, category)
                .where(category.name.eq(categoryName))
                .orderBy(sku.price.desc())
                .fetchFirst();
    }

    public List<SkuDto> getAllItems() {

        QSku sku = QSku.sku;
        QBrand brand = QBrand.brand;
        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.constructor(SkuDto.class,
                        sku.id, brand.name, category.name, sku.price))
                .from(sku)
                .leftJoin(sku.brand, brand)
                .leftJoin(sku.category, category)
                .orderBy(sku.updateDate.desc())
                .fetch();
    }
}
