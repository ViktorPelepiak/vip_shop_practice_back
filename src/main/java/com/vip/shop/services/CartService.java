package com.vip.shop.services;

import com.vip.shop.exceptions.ElementNotFoundException;
import com.vip.shop.models.Cart;

import java.util.List;

public interface CartService {

    Cart getActiveCart() throws ElementNotFoundException;

    Cart addProductToCart(Long productId) throws ElementNotFoundException;

    Cart orderActiveCart() throws ElementNotFoundException;

    List<Cart> getOrderedCartsForAuthoredUsers();

    Cart registerPayment(Long order_id);
}
