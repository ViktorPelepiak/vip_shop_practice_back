package com.vip.shop.dto;

public class SaveProductDto {
    private String title;
    private String description;
    private float price;
    private Long categoryId;
    private byte[] image;

    public String getTitle() {
        return title;
    }

    public SaveProductDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SaveProductDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public SaveProductDto setPrice(float price) {
        this.price = price;
        return this;
    }

    public byte[] getImage() {
        return image;
    }

    public SaveProductDto setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public SaveProductDto setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }
}
