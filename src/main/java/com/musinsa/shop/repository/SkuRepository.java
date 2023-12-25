package com.musinsa.shop.repository;

import com.musinsa.shop.domain.Brand;
import com.musinsa.shop.domain.Category;
import com.musinsa.shop.domain.Sku;
import com.musinsa.shop.dto.SkuViewInterface;
import com.musinsa.shop.repository.querydsl.SkuQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SkuRepository extends JpaRepository<Sku, String>, SkuQueryRepository {

    // TODO: QueryDSL 로 여러 쿼리로 분할하여 구현하는 방법 고민해보기
    /**
     * 구현 1) 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회
     * JPQL 또는 QueryDSL 기능이 제한적이라 네이티브 쿼리 사용
     *
     * @return List<SkuViewInterface>
     */
    @Query(value = "SELECT *\n" +
            "FROM (\n" +
            "    SELECT\n" +
            "        sku.category_id,\n" +
            "        brand.name AS brand,\n" +
            "        category.name AS category,\n" +
            "        sku.price,\n" +
            "        ROW_NUMBER() OVER (PARTITION BY sku.category_id ORDER BY sku.price ASC) as rn\n" +
            "    FROM\n" +
            "        sku \n" +
            "    JOIN\n" +
            "        brand ON sku.brand_id = brand.id \n" +
            "    JOIN\n" +
            "        category ON sku.category_id = category.id \n" +
            ") AS sub\n" +
            "WHERE\n" +
            "    sub.rn = 1\n" +
            "ORDER BY\n" +
            "    sub.category_id;", nativeQuery = true)
    List<SkuViewInterface> getLowestPriceItems();

    Optional<Sku> findByBrandAndCategory(Brand brand, Category category);
    void deleteByBrandAndCategory(Brand brand, Category category);


}
