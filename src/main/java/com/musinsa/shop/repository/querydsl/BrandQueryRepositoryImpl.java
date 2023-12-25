package com.musinsa.shop.repository.querydsl;

import com.musinsa.shop.domain.QBrand;
import com.musinsa.shop.dto.BrandDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BrandQueryRepositoryImpl implements BrandQueryRepository{

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
