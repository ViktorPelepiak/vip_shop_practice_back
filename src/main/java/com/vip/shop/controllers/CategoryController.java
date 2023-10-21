package com.vip.shop.controllers;

import com.vip.shop.models.ProductCategory;
import com.vip.shop.rest.GenericResponse;
import com.vip.shop.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {

    private final ProductCategoryService productCategoryService;

    @Autowired
    public CategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public GenericResponse<List<ProductCategory>> getAll() {
        return GenericResponse.of(productCategoryService.findAll());
    }
}
