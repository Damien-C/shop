package com.musinsa.shop.repository;

import com.musinsa.shop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findByName(String categoryName);

}
