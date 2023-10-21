package com.vip.shop.services;

import com.vip.shop.dto.SaveProductDto;
import com.vip.shop.models.Product;

import java.util.List;

public interface ProductService {
    Product save(SaveProductDto productDto);

    List<Product> getAll();

    String getImageByProductId(Long id);
}
