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

import static com.musinsa.shop.domain.QCategory.category;

@RequiredArgsConstructor
@Repository
public class BrandQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<BrandDto> getBrandList() {

        QBrand brand = QBrand.brand;

        return queryFactory
                .select(Projections.constructor(BrandDto.class,
                        brand.id, brand.name
                ))
                .from(brand)
                .fetch();

    }
}
