package com.vip.shop.services.impl;

import com.vip.shop.models.ProductCategory;
import com.vip.shop.repository.ProductCategoryRepository;
import com.vip.shop.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory findById(Long id) {
        return productCategoryRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public ProductCategory findByName(String name) {
        return productCategoryRepository.findFirstByName(name);
    }
}
