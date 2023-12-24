package com.musinsa.shop.controller.rest;

import com.musinsa.shop.dto.ApiDataResponse;
import com.musinsa.shop.dto.ShopRequest;
import com.musinsa.shop.dto.SkuDto;
import com.musinsa.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/api")
@RestController
public class ApiShopAdminController {
    private final ShopService shopService;

    // 4. 운영자는 새로운 브랜드를 등록하고, 모든 브랜드의 상품을 추가, 변경, 삭제할 수 있어야 합니다.

    // 브랜드 등록
    @PostMapping("/brand")
    public ApiDataResponse<?> createBrand(@RequestBody ShopRequest request){
        shopService.createBrand(request.getBrandName());
        return ApiDataResponse.ok();
    }

    // 브랜드 삭제
    @DeleteMapping(value = "/brand")
    public ApiDataResponse<?> deleteBrandByName(@RequestBody ShopRequest request){
        shopService.deleteBrand(request.getBrandName());
        return ApiDataResponse.ok();
    }

    // 브랜드 이름 수정
    @PatchMapping(value = "/brand")
    public ApiDataResponse<?> updateBrandNameByName(@RequestBody ShopRequest request){
        shopService.updateBrandName(request.getBrandName(), request.getNewName());
        return ApiDataResponse.ok();
    }

    // 카테고리 등록
    @PostMapping("/category")
    public ApiDataResponse<?> createCategory(@RequestBody ShopRequest request){
        shopService.createCategory(request.getCategoryName());
        return ApiDataResponse.ok();
    }

    // 카테고리 삭제
    @DeleteMapping(value = "/category")
    public ApiDataResponse<?> deleteCategoryByName(@RequestBody ShopRequest request){
        shopService.deleteCategory(request.getCategoryName());
        return ApiDataResponse.ok();
    }

    // 카테고리 이름 수정
    @PatchMapping(value = "/category")
    public ApiDataResponse<?> updateCategoryNameByName(@RequestBody ShopRequest request){
        shopService.updateCategoryName(request.getCategoryName(), request.getNewName());
        return ApiDataResponse.ok();
    }

    // 아이템 등록
    @Deprecated
    @PostMapping("/item")
    public ApiDataResponse<?> createSkuItem(@RequestBody ShopRequest request){
        return ApiDataResponse.of(shopService.createSkuItem(request.getBrandName(), request.getCategoryName(), request.getPrice()));
    }

    // 아이템 가격 수정
    // 한 브랜드와 카테고리에 여러 상품이 생갈경우 사용불가능.
    @Deprecated
    @PatchMapping(value = "/item")
    public ApiDataResponse<?> updateItemPriceByBrandNameAndCategory(@RequestBody ShopRequest request){
        shopService.updateSkuItemPriceByBrandNameAndCategory(request.getBrandName(), request.getCategoryName(), request.getPrice());
        return ApiDataResponse.ok();
    }


    // 아이템 전체 수정
    @PutMapping(value = "/item")
    public ApiDataResponse<?> updateItemById(@RequestBody ShopRequest request){
        shopService.updateSkuItemById(request.getId(), request.getBrandName(), request.getCategoryName(), request.getPrice());
        return ApiDataResponse.ok();
    }

    // 아이템 삭제
    @DeleteMapping(value = "/item")
    public ApiDataResponse<?> deleteItemById(@RequestBody ShopRequest request){
        shopService.deleteSkuItemById(request.getId());
        return ApiDataResponse.ok();
    }

}
