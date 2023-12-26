package com.musinsa.shop.repository.querydsl;

import com.musinsa.shop.dto.CategoryDto;
import java.util.List;

public interface CategoryQueryRepository {
    List<CategoryDto> getCategoryList();
}
