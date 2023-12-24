package com.musinsa.shop.controller.view;

import com.musinsa.shop.dto.BrandDto;
import com.musinsa.shop.dto.CategoryDto;
import com.musinsa.shop.service.ShopService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ShopAdminController {

    private final ShopService shopService;
    @GetMapping("/admin")
    public ModelAndView admin(@Nullable @RequestParam String pageType){

        ModelAndView view = new ModelAndView();

        if(Objects.equals(pageType, "brand")){
            List<BrandDto> brandList = shopService.getBrandList();
            view.addObject("brandList", brandList);
        }
        else if(Objects.equals(pageType, "category")){
            List<CategoryDto> categoryList = shopService.getCategoryList();
            view.addObject("categoryList", categoryList);
        }
        else if(Objects.equals(pageType, "skuItem")){
            List<BrandDto> brandList = shopService.getBrandList();
            view.addObject("brandList", brandList);
            List<CategoryDto> categoryList = shopService.getCategoryList();
            view.addObject("categoryList", categoryList);
        }

        view.setViewName("view/admin");
        return view;
    }
}
