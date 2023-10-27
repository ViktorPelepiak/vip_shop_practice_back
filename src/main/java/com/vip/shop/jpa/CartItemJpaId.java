package com.vip.shop.jpa;

import java.io.Serializable;
import java.util.Objects;

public class CartItemJpaId implements Serializable {
    private Long cart;
    private Long product;

    public CartItemJpaId() {
    }

    public CartItemJpaId(Long cart, Long product) {
        this.cart = cart;
        this.product = product;
    }

    public Long getCart() {
        return cart;
    }

    public CartItemJpaId setCart(Long cart) {
        this.cart = cart;
        return this;
    }

    public Long getProduct() {
        return product;
    }

    public CartItemJpaId setProduct(Long product) {
        this.product = product;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemJpaId)) return false;
        CartItemJpaId that = (CartItemJpaId) o;
        return getCart().equals(that.getCart()) && getProduct().equals(that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCart(), getProduct());
    }
}
