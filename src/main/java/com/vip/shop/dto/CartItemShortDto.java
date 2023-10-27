package com.vip.shop.dto;

import com.vip.shop.models.CartItem;

public class CartItemShortDto {
    private String title;
    private Integer quantity;
    private Float price;

    public String getTitle() {
        return title;
    }

    public CartItemShortDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartItemShortDto setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public CartItemShortDto setPrice(Float price) {
        this.price = price;
        return this;
    }

    public static CartItemShortDto toDto(CartItem cartItem) {
        return new CartItemShortDto()
                .setTitle(cartItem.getProduct().getTitle())
                .setQuantity(cartItem.getQuantity())
                .setPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice());
    }
}
