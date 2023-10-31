package com.vip.shop.controllers;

import com.vip.shop.dto.CartDto;
import com.vip.shop.dto.OrderShortDto;
import com.vip.shop.exceptions.ElementNotFoundException;
import com.vip.shop.rest.GenericResponse;
import com.vip.shop.rest.LiqPayResponse;
import com.vip.shop.services.CartService;
import com.vip.shop.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("carts")
public class CartController {

    private final CartService cartService;
    private final PaymentService paymentService;

    @Autowired
    public CartController(CartService cartService, PaymentService paymentService) {
        this.cartService = cartService;
        this.paymentService = paymentService;
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
    GenericResponse<List<OrderShortDto>> getOrderedCarts() {
        return GenericResponse.of(
                cartService.getOrderedCartsForAuthoredUsers().stream()
                        .map(OrderShortDto::toDto)
                        .map(order -> order.setPayButton(paymentService.preparePaymentButton(order)))
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("payment")
    void registerPayment(@RequestBody LiqPayResponse response) {
        cartService.registerPayment(Long.valueOf(response.getOrder_id()));
    }
}
