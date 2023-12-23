package com.musinsa.shop.exception;

import com.musinsa.shop.constant.StatusCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    private final StatusCode errorCode;
    private final String detailErrorMessage;

    public GeneralException(StatusCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.detailErrorMessage = "";
    }

    public GeneralException(StatusCode errorCode, String detailMessage) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.detailErrorMessage = detailMessage;
    }
}
