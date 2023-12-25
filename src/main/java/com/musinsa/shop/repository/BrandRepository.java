package com.musinsa.shop.repository;

import com.musinsa.shop.domain.Brand;
import com.musinsa.shop.repository.querydsl.BrandQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, String>, BrandQueryRepository {
    Optional<Brand> findByName(String name);
}
