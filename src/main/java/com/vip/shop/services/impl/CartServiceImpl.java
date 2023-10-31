package com.vip.shop.services.impl;

import com.vip.shop.enums.CartStatus;
import com.vip.shop.exceptions.ElementNotFoundException;
import com.vip.shop.models.Cart;
import com.vip.shop.models.CartItem;
import com.vip.shop.models.User;
import com.vip.shop.repository.CartItemRepository;
import com.vip.shop.repository.CartRepository;
import com.vip.shop.services.CartService;
import com.vip.shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;


    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductService productService, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart getActiveCart() throws ElementNotFoundException {
        return cartRepository
                .getActiveCartForUser(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public Cart addProductToCart(Long productId) throws ElementNotFoundException {
        User activeUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Cart activeCart = cartRepository.getActiveCartForUser(activeUser)
                .orElse(Cart.createNewCartForUser(activeUser));
        activeCart.addItem(productService.getProductById(productId), 1);

        activeCart = cartRepository.save(activeCart);

        activeCart.setItems(activeCart.getItems().stream()
                .map(cartItemRepository::save)
                .collect(Collectors.toSet()));

        return cartRepository.save(activeCart);
    }

    @Override
    public Cart orderActiveCart() throws ElementNotFoundException {
        return cartRepository.save(cartRepository
                .getActiveCartForUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .orElseThrow(ElementNotFoundException::new)
                .setStatus(CartStatus.ORDERED)
                .setOrderingDate(LocalDateTime.now())
        );
    }

    @Override
    public List<Cart> getOrderedCartsForAuthoredUsers() {
        return cartRepository.getAllOrderedCartsForUser(
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );
    }

    @Override
    public Cart registerPayment(Long order_id) {
        return cartRepository.save(
                cartRepository.findById(order_id)
                        .orElseThrow(IllegalArgumentException::new)
                .setStatus(CartStatus.PAYED)
        );
    }
}
