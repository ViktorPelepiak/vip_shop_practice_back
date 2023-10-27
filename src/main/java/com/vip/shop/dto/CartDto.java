package com.vip.shop.dto;

import com.vip.shop.models.Cart;

import java.util.List;
import java.util.stream.Collectors;

public class CartDto {
    private Long id;
    private String status;
    private List<CartItemShortDto> items;
    private Float price;

    public Long getId() {
        return id;
    }

    public CartDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public CartDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public List<CartItemShortDto> getItems() {
        return items;
    }

    public CartDto setItems(List<CartItemShortDto> items) {
        this.items = items;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public CartDto setPrice(Float price) {
        this.price = price;
        return this;
    }

    public static CartDto toDto(Cart cart) {
        return new CartDto()
                .setId(cart.getId())
                .setStatus(cart.getStatus().name())
                .setItems(cart.getItems().stream().map(CartItemShortDto::toDto).collect(Collectors.toList()))
                .setPrice(cart.getItems().stream()
                        .map(item -> item.getQuantity() * item.getProduct().getPrice())
                        .reduce(Float::sum).orElse(0f)
                );
    }
}
