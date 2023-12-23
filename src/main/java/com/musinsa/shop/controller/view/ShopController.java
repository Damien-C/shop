package com.musinsa.shop.controller.view;

import com.musinsa.shop.dto.CategoryDto;
import com.musinsa.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/")
    public ModelAndView root(){

        List<CategoryDto> categoryList = shopService.getCategoryList();
        ModelAndView view = new ModelAndView();
        view.addObject("categoryList", categoryList);
        view.setViewName("view/main");
        return view;
    }

}
