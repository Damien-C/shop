package com.musinsa.shop.repository;

import com.musinsa.shop.domain.Category;
import com.musinsa.shop.repository.querydsl.CategoryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String>, CategoryQueryRepository {

    Optional<Category> findByName(String categoryName);

}
