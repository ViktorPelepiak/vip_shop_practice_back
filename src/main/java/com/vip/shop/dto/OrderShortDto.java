package com.vip.shop.dto;

import com.vip.shop.models.Cart;

public class OrderShortDto {
    private Long id;
    private String status;
    private String orderingDate;
    private Float price;
    private String payButton;

    public Long getId() {
        return id;
    }

    public OrderShortDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderShortDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getOrderingDate() {
        return orderingDate;
    }

    public OrderShortDto setOrderingDate(String orderingDate) {
        this.orderingDate = orderingDate;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public OrderShortDto setPrice(Float price) {
        this.price = price;
        return this;
    }

    public String getPayButton() {
        return payButton;
    }

    public OrderShortDto setPayButton(String payButton) {
        this.payButton = payButton;
        return this;
    }

    public static OrderShortDto toDto(Cart cart) {
        return new OrderShortDto()
                .setId(cart.getId())
                .setStatus(cart.getStatus().name())
                .setPrice(cart.getItems().stream()
                        .map(item -> {return item.getQuantity() * item.getProduct().getPrice();})
                        .reduce(Float::sum).orElse(0f)
                )
                .setOrderingDate(String.format("%d/%d/%d",
                        cart.getOrderingDate().getDayOfMonth(),
                        cart.getOrderingDate().getMonthValue(),
                        cart.getOrderingDate().getYear()
                ));
    }
}
