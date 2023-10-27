package com.vip.shop.dto;

import com.vip.shop.models.Product;

public class ProductDisplayDto {
    private Long id;
    private String title;
    private float price;
    private String description;
    private String category;
    private String image;

    public Long getId() {
        return id;
    }

    public ProductDisplayDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProductDisplayDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public ProductDisplayDto setPrice(float price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDisplayDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ProductDisplayDto setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ProductDisplayDto setImage(String image) {
        this.image = image;
        return this;
    }

    public static ProductDisplayDto toDto(Product product) {
        return new ProductDisplayDto()
                .setId(product.getId())
                .setTitle(product.getTitle())
                .setCategory(product.getCategory().getName())
                .setDescription(product.getDescription())
                .setPrice(product.getPrice())
                .setImage(product.getImage());
    }
}
