package com.musinsa.shop.repository;

import com.musinsa.shop.domain.Brand;
import com.musinsa.shop.domain.Category;
import com.musinsa.shop.domain.Sku;
import com.musinsa.shop.dto.SkuViewInterface;
import com.musinsa.shop.repository.querydsl.SkuQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
    @Query(value =
            """
                    SELECT brand.name AS brand, category.name AS category, sku.price AS price FROM sku\s
                    INNER JOIN brand ON brand.id = sku.brand_id
                    INNER JOIN category ON category.id = sku.category_id
                    WHERE sku.id IN (
                    \tSELECT MAX(s.id) FROM sku s
                    \tINNER JOIN (
                    \t\tSELECT category_id, MIN(price) AS min_price\s
                    \t\tFROM sku\s
                    \t\tGROUP BY category_id) AS c\s
                    \tON s.category_id = c.category_id AND s.price = c.min_price
                    \tGROUP BY c.category_id) order by category.id;""", nativeQuery = true)
    List<SkuViewInterface> getLowestPriceItems();

    Optional<Sku> findByBrandAndCategory(Brand brand, Category category);
    void deleteByBrandAndCategory(Brand brand, Category category);


}
