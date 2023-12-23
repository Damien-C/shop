package com.musinsa.shop.exception;

import com.musinsa.shop.constant.StatusCode;
import lombok.Getter;

@Getter
public class GeneralApiException extends RuntimeException {
    private final StatusCode errorCode;
    private final String detailErrorMessage;

    public GeneralApiException(StatusCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.detailErrorMessage = "";
    }

    public GeneralApiException(StatusCode errorCode, String detailMessage) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.detailErrorMessage = detailMessage;
    }
}
