package com.musinsa.shop.repository;

import com.musinsa.shop.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, String> {
    Optional<Brand> findByName(String name);
}
