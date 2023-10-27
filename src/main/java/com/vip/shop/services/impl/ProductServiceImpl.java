package com.vip.shop.services.impl;

import com.vip.shop.dto.SaveProductDto;
import com.vip.shop.exceptions.ElementNotFoundException;
import com.vip.shop.models.Product;
import com.vip.shop.repository.ProductRepository;
import com.vip.shop.services.ImageService;
import com.vip.shop.services.ProductCategoryService;
import com.vip.shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@PropertySource("/image.properties")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService;
    private final ImageService imageService;
    private final Environment environment;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryService productCategoryService,
                              ImageService imageService, Environment environment) {
        this.productRepository = productRepository;
        this.productCategoryService = productCategoryService;
        this.imageService = imageService;
        this.environment = environment;
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
    public Product getProductById(Long id) throws ElementNotFoundException {
        return this.productRepository.findById(id).orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public String getImageByProductId(Long id) {
        return productRepository.findById(id).orElseThrow(IllegalArgumentException::new).getImage();
    }
}
