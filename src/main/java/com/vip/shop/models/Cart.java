package com.vip.shop.models;

import com.vip.shop.enums.CartStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    private LocalDateTime orderingDate;

    @OneToMany(mappedBy = "cart")
    private Set<CartItem> items;

    public Long getId() {
        return id;
    }

    public Cart setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Cart setUser(User user) {
        this.user = user;
        return this;
    }

    public CartStatus getStatus() {
        return status;
    }

    public Cart setStatus(CartStatus status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getOrderingDate() {
        return orderingDate;
    }

    public Cart setOrderingDate(LocalDateTime orderingDate) {
        this.orderingDate = orderingDate;
        return this;
    }

    public Set<CartItem> getItems() {
        return items;
    }

    public Cart setItems(Set<CartItem> items) {
        this.items = items;
        return this;
    }

    public Cart addItem(Product product, Integer quantity) {
        boolean isItemInCartAlready = false;
        for (CartItem item : this.items) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + quantity);
                isItemInCartAlready = true;
                break;
            }
        }
        if (isItemInCartAlready) return this;
        this.items.add(new CartItem()
                .setCart(this)
                .setProduct(product)
                .setQuantity(quantity)
        );
        return this;
    }

    public static Cart createNewCartForUser(User user) {
        return new Cart()
                .setUser(user)
                .setStatus(CartStatus.NEW)
                .setItems(new HashSet<>());
    }
}
