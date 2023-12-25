package com.musinsa.shop.repository.querydsl;

import com.musinsa.shop.domain.QCategory;
import com.musinsa.shop.dto.BrandDto;
import com.musinsa.shop.dto.CategoryDto;
import com.querydsl.core.types.Projections;

import java.util.List;

public interface CategoryQueryRepository {
    List<CategoryDto> getCategoryList();
}
