package com.musinsa.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ShopRequest{
    private String brandName;
    private String categoryName;
    private BigDecimal price;

    private String newName;
}
