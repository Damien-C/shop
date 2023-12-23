package com.musinsa.shop.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@ControllerAdvice(annotations = Controller.class)
public class BaseExceptionHandler {
//    @ExceptionHandler
//    public ModelAndView handleSellwayException(GeneralException e, WebRequest webRequest, HttpServletResponse response) {
//        log.debug("errorCode : {}, webRequest: {}, message: {}", e.getErrorCode(), webRequest, e.getDetailErrorMessage());
//
//        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
//
//        ModelAndView view = new ModelAndView();
//        view.addObject("statusCode", e.getErrorCode().getHttpCode());
//        view.addObject("errorDescription", e.getErrorCode().getDescription());
//        view.addObject("detailErrorMessage", e.getDetailErrorMessage());
//        view.setViewName("/error");
//        return view;
//    }
//
//    @ExceptionHandler
//    public ModelAndView exception(Exception e, HttpServletResponse response) {
//        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
//        ErrorCode errorCode = httpStatus.is4xxClientError() ? ErrorCode.INVALID_REQUEST : ErrorCode.INTERNAL_SERVER_ERROR;
//
//        if (httpStatus == HttpStatus.OK) {
//            httpStatus = HttpStatus.FORBIDDEN;
//            errorCode = ErrorCode.INVALID_REQUEST;
//        }
//
//        log.debug("exception : {}, httpStatus: {}", e.getMessage(), httpStatus);
//        ModelAndView view = new ModelAndView();
//        view.addObject("statusCode", httpStatus.value());
//        view.addObject("errorDescription", errorCode.getDescription());
//        view.setViewName("/error");
//        return view;
//
//    }
//

}

