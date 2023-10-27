package com.vip.shop.controllers;

import com.vip.shop.dto.ProductDisplayDTO;
import com.vip.shop.dto.SaveProductDto;
import com.vip.shop.exceptions.ElementNotFoundException;
import com.vip.shop.models.Product;
import com.vip.shop.rest.GenericResponse;
import com.vip.shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
@PropertySource("/image.properties")
public class ProductController {

    private final ProductService productService;
    private final Environment environment;

    @Autowired
    public ProductController(ProductService productService, Environment environment) {
        this.productService = productService;
        this.environment = environment;
    }

    @GetMapping
    public GenericResponse<List<ProductDisplayDTO>> getAll() {
        return GenericResponse.of(productService.getAll().stream()
                .map(ProductDisplayDTO::toDto)
                .map(product -> product.setImage(environment.getProperty("application.url") + "/products/" + product.getId() + "/image"))
                .collect(Collectors.toList()));
    }

    @CrossOrigin
    @GetMapping(value = "{id}/image",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable Long id) throws IOException {
        return new FileInputStream(environment.getProperty("new.image.path")
                + productService.getImageByProductId(id)).readAllBytes();
    }

    @GetMapping("{id}")
    public GenericResponse<ProductDisplayDTO> getProductById(@PathVariable Long id) {
        try {
            ProductDisplayDTO product =  ProductDisplayDTO.toDto(this.productService.getProductById(id));
            product.setImage(environment.getProperty("application.url") + "/products/" + product.getId() + "/image");
            return GenericResponse.of(product);
        } catch (ElementNotFoundException e) {
            return GenericResponse.error(e.getMessage());
        }
    }

    @PostMapping
    public GenericResponse<Product> save(@RequestBody SaveProductDto productDto) {
        try {
            return GenericResponse.of(productService.save(productDto));
        } catch (Exception e) {
            return GenericResponse.error(e.getMessage());
        }
    }
}
