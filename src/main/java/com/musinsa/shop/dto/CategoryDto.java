package com.musinsa.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CategoryDto {
    private String id;
    private String name;

    public CategoryDto(String name){
        this.name = name;
    }
}
