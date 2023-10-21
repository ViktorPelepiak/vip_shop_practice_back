package com.vip.shop.services;

import com.vip.shop.models.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> findAll();

    ProductCategory findById(Long id);

    ProductCategory findByName(String name);
}
