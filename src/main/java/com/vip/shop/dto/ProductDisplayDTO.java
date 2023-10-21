package com.vip.shop.dto;

import com.vip.shop.models.Product;
import org.springframework.core.env.Environment;

public class ProductDisplayDTO {
    private Long id;
    private String title;
    private float price;
    private String description;
    private String category;
    private String image;

    public Long getId() {
        return id;
    }

    public ProductDisplayDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProductDisplayDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public ProductDisplayDTO setPrice(float price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDisplayDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ProductDisplayDTO setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ProductDisplayDTO setImage(String image) {
        this.image = image;
        return this;
    }

    public static ProductDisplayDTO toDto(Product product) {
        return new ProductDisplayDTO()
                .setId(product.getId())
                .setTitle(product.getTitle())
                .setCategory(product.getCategory().getName())
                .setDescription(product.getDescription())
                .setPrice(product.getPrice())
                .setImage(product.getImage());
    }
}
