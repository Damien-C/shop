package com.musinsa.shop.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
    // NORMAL
    NORMAL(200, "정상"),

    // GENERAL
    INTERNAL_SERVER_ERROR(500, "서버에 오류가 발생하였습니다."),
    INVALID_REQUEST(400, "잘못된 요청입니다."),
    ITEM_ALREADY_EXISTS(400, "해당 브랜드와 카테고리에 아이템이 존재합니다."),
    BRAND_NAME_ALREADY_EXISTS(400, "브랜드 이름이 존재합니다."),
    CATEGORY_NAME_ALREADY_EXISTS(400, "카테고리 이름이 존재합니다."),
    DATA_ACCESS_ERROR(500, "데이터를 불러오지 못했습니다."),
    PRODUCT_NOT_FOUND(404, "상품을 찾지 못했습니다."),
    BRAND_NAME_NOT_FOUND(404, "브랜드 이름이 존재하지 않습니다."),
    CATEGORY_NAME_NOT_FOUND(404, "카테고리 이름이 존재하지 않습니다."),
    ITEM_NOT_FOUND(404, "브랜드와 카테고리에 해당하는 아이템이 존재하지 않습니다."),

    // SPRING
    CLIENT_ERROR(400, "잘못된 요청입니다."),
    INTERNAL_ERROR(500, "서버에 오류가 발생하였습니다.");


    private final Integer httpCode;
    private final String description;
}
