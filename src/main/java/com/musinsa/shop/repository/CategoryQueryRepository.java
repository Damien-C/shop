package com.musinsa.shop.repository;

import com.musinsa.shop.domain.QBrand;
import com.musinsa.shop.domain.QCategory;
import com.musinsa.shop.domain.QSku;
import com.musinsa.shop.dto.BrandDto;
import com.musinsa.shop.dto.CategoryDto;
import com.musinsa.shop.dto.SkuDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<SkuDto> getAllItems() {

        QSku sku = QSku.sku;
        QBrand brand = QBrand.brand;
        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.constructor(SkuDto.class,
                        brand.name, category.name, sku.price
                        ))
                .from(sku)
                .leftJoin(sku.brand, brand)
                .leftJoin(sku.category, category)
                .fetch();
    }

    public List<CategoryDto> getCategoryList() {

        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.constructor(CategoryDto.class,
                        category.id, category.name
                ))
                .from(category)
                .fetch();

    }
}
