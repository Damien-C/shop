package com.musinsa.shop.repository.querydsl;

import com.musinsa.shop.domain.QBrand;
import com.musinsa.shop.domain.QCategory;
import com.musinsa.shop.dto.BrandDto;
import com.musinsa.shop.dto.CategoryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryQueryRepositoryImpl implements CategoryQueryRepository{

    private final JPAQueryFactory queryFactory;
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
