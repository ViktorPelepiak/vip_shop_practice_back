package com.vip.shop.models;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class CartItem {
    @Id
    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;

    @Id
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    private int quantity;
}
