package com.musinsa.shop.repository.querydsl;

import com.musinsa.shop.dto.BrandDto;

import java.util.List;

public interface BrandQueryRepository {
    List<BrandDto> getBrandList();
}
