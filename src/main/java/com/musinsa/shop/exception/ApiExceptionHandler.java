package com.musinsa.shop.exception;

import com.musinsa.shop.constant.StatusCode;
import com.musinsa.shop.dto.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(GeneralApiException.class)
    public ResponseEntity<Object> handleMusinsaApiException(GeneralApiException e, WebRequest request) {
        log.info("errorCode : {}, url: {}, message: {}", e.getErrorCode(), request, e.getDetailErrorMessage());
        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false, e.getErrorCode(), e.getMessage() + "  " + e.getDetailErrorMessage()),
                HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    // Unknown Exception Handler
    @ExceptionHandler
    public ResponseEntity<Object> handleUnknownRequest(Exception e, WebRequest request) {
        log.error("url: {}, message: {}", request, e.getMessage());
        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false, StatusCode.INTERNAL_SERVER_ERROR, StatusCode.INTERNAL_SERVER_ERROR.getDescription()),
                HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST,
                request
        );
    }


    @Override
    @Nullable
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        StatusCode errorCode = statusCode.is4xxClientError() ?
                StatusCode.CLIENT_ERROR :
                StatusCode.INTERNAL_ERROR;

        log.info("########## : {}, {}", e.getMessage(), e.getLocalizedMessage());
        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false, errorCode, errorCode.getDescription()),
                headers,
                statusCode,
                request
        );
    }
}
