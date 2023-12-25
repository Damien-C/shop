package com.musinsa.shop.repository.querydsl;

import com.musinsa.shop.domain.QBrand;
import com.musinsa.shop.dto.BrandDto;
import com.querydsl.core.types.Projections;

import java.util.List;

public interface BrandQueryRepository {
    List<BrandDto> getBrandList();
}
