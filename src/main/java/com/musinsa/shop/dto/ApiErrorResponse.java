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
