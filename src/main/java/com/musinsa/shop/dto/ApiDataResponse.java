package com.musinsa.shop.dto;

import com.musinsa.shop.constant.StatusCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 공통적으로 사용하는 REST API 응답 값을 담는 객체로, 상황에 맞게 데이터 타입을 넣어서 사용한다.
 * @param <T>
 */
@Getter
@ToString

@EqualsAndHashCode(callSuper = true)
public class ApiDataResponse<T> extends ApiErrorResponse {
    private final T data;

    public ApiDataResponse() {
        super(true, StatusCode.NORMAL.name(), StatusCode.NORMAL.getDescription());
        this.data = null;
    }
    public ApiDataResponse(T data) {
        super(true, StatusCode.NORMAL.name(), StatusCode.NORMAL.getDescription());
        this.data = data;
    }

    /**
     *
     * 기본 생성함수
     *
     * @param data T
     * @return ApiDataResponse
     * @param <T> Generic
     */
    public static <T> ApiDataResponse<T> of(T data) {
        return new ApiDataResponse<>(data);
    }
    public static <T> ApiDataResponse<T> ok() {
        return new ApiDataResponse<>();
    }
}
