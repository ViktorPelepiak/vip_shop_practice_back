package com.vip.shop.services.impl;

import com.vip.shop.dto.SaveProductDto;
import com.vip.shop.models.Product;
import com.vip.shop.repository.ProductRepository;
import com.vip.shop.services.ImageService;
import com.vip.shop.services.ProductCategoryService;
import com.vip.shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService;
    private final ImageService imageService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryService productCategoryService,
                              ImageService imageService) {
        this.productRepository = productRepository;
        this.productCategoryService = productCategoryService;
        this.imageService = imageService;
    }

    @Override
    public Product save(SaveProductDto productDto) {
        String imageUrl =  this.imageService.saveImage(productDto.getImage(),
                UUID.randomUUID().toString());
        return productRepository.save(new Product()
                .setTitle(productDto.getTitle())
                .setDescription(productDto.getDescription())
                .setPrice(productDto.getPrice())
                .setImage(imageUrl)
                .setCategory(
                        productCategoryService.findById(productDto.getCategoryId())
                )
        );
    }

    @Override
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    @Override
    public String getImageByProductId(Long id) {
        return productRepository.findById(id).orElseThrow(IllegalArgumentException::new).getImage();
    }
}
