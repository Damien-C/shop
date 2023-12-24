package com.musinsa.shop.controller.view;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    @ModelAttribute("servletPath")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }

    @ModelAttribute("queryString")
    String getRequestQueryString(HttpServletRequest request) {
        return request.getQueryString();
    }
}