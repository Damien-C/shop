package com.musinsa.shop.dto;

import com.musinsa.shop.constant.StatusCode;
import lombok.*;

/**
 * REST API 요청에 대한 에러를 반환할때 사용한다.
 */
@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiErrorResponse {

    // 성공,실패 여부
    private final Boolean success;

    // 상태코드
    private final String statusCode;

    // 메세지
    private final String message;

    /**
     *
     * 에러코드 & 메세지를 자유롭게 작성할때 사용
     *
     * @param success Boolean
     * @param errorCode String
     * @param message String
     * @return ApiErrorResponse
     */
    public static ApiErrorResponse of(Boolean success, String errorCode, String message) {
        return new ApiErrorResponse(success, errorCode, message);
    }

    /**
     *
     * StatusCode 에서 제공하는 에러코드 & 메세지 사용을 위한 생성함수
     *
     * @param success Boolean
     * @param statusCode StatusCode
     * @return ApiErrorResponse
     */
    public static ApiErrorResponse of(Boolean success, StatusCode statusCode) {
        return new ApiErrorResponse(success, statusCode.name(), statusCode.getDescription());
    }

    /**
     *
     * Exception 에서 제공하는 메세지 사용을 위한 생성함수
     *
     * @param success Boolean
     * @param statusCode StatusCode
     * @param e Exception
     * @return ApiErrorResponse
     */
    public static ApiErrorResponse of(Boolean success, StatusCode statusCode, Exception e) {
        return new ApiErrorResponse(success, statusCode.name(), e.getMessage());
    }

    /**
     *
     * StatusCode 에서 제공하는 에러코드와 커스텀 메세지 사용을 위한 생성함수
     *
     * @param success Boolean
     * @param statusCode StatusCode
     * @param message String
     * @return ApiErrorResponse
     */
    public static ApiErrorResponse of(Boolean success, StatusCode statusCode, String message) {
        return new ApiErrorResponse(success, statusCode.name(), message);
    }


}
