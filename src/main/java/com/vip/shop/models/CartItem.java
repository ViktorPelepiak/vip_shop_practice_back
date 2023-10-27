package com.vip.shop.models;

import com.vip.shop.jpa.CartItemJpaId;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
@IdClass(CartItemJpaId.class)
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

    public Cart getCart() {
        return cart;
    }

    public CartItem setCart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public CartItem setProduct(Product product) {
        this.product = product;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
