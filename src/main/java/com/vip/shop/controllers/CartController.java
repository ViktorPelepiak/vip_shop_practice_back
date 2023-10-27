package com.vip.shop.controllers;

import com.vip.shop.dto.CartDto;
import com.vip.shop.exceptions.ElementNotFoundException;
import com.vip.shop.rest.GenericResponse;
import com.vip.shop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("active")
    public GenericResponse<CartDto> getActiveCart() {
        try {
            return GenericResponse.of(CartDto.toDto(cartService.getActiveCart()));
        } catch (ElementNotFoundException e) {
            return GenericResponse.empty();
        }
    }

    @GetMapping("addProduct/{productId}")
    public GenericResponse<CartDto> addProductToCart(@PathVariable Long productId) {
        try {
            return GenericResponse.of(CartDto.toDto(cartService.addProductToCart(productId)));
        } catch (ElementNotFoundException e) {
            return GenericResponse.error(e.getMessage());
        }
    }

    @PutMapping("active/order")
    GenericResponse<CartDto> orderActiveCart() {
        try {
            return GenericResponse.of(CartDto.toDto(cartService.orderActiveCart()));
        } catch (ElementNotFoundException e) {
            return GenericResponse.error(e.getMessage());
        }
    }

    @GetMapping("ordered")
    GenericResponse<List<CartDto>> getOrderedCarts() {
        return GenericResponse.of(
                cartService.getOrderedCartsForAuthoredUsers().stream()
                        .map(CartDto::toDto)
                        .collect(Collectors.toList())
        );
    }
}
