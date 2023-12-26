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
    @Query(value = "SELECT brand.name, category.name, sku.price FROM sku \n" +
            "INNER JOIN brand ON brand.id = sku.brand_id\n" +
            "INNER JOIN category ON category.id = sku.category_id\n" +
            "WHERE sku.id IN (\n" +
            "\tSELECT MAX(s.id) FROM sku s\n" +
            "\tINNER JOIN (\n" +
            "\t\tSELECT category_id, MIN(price) AS min_price \n" +
            "\t\tFROM sku \n" +
            "\t\tGROUP BY category_id) AS c \n" +
            "\tON s.category_id = c.category_id AND s.price = c.min_price\n" +
            "\tGROUP BY c.category_id) order by category.id;", nativeQuery = true)
    List<SkuViewInterface> getLowestPriceItems();

    Optional<Sku> findByBrandAndCategory(Brand brand, Category category);
    void deleteByBrandAndCategory(Brand brand, Category category);


}
