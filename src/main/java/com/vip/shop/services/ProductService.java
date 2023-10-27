package com.vip.shop.services;

import com.vip.shop.dto.SaveProductDto;
import com.vip.shop.exceptions.ElementNotFoundException;
import com.vip.shop.models.Product;

import java.util.List;

public interface ProductService {
    Product save(SaveProductDto productDto);

    List<Product> getAll();

    Product getProductById(Long id) throws ElementNotFoundException;

    String getImageByProductId(Long id);
}
