package com.vip.shop.repository;

import com.vip.shop.enums.CartStatus;
import com.vip.shop.models.Cart;
import com.vip.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> getAllByUserAndStatusNot(User user, CartStatus status);

    Optional<Cart> getFirstByUserAndStatus(User user, CartStatus status);

    default List<Cart> getAllOrderedCartsForUser(User user) {
        return this.getAllByUserAndStatusNot(user, CartStatus.NEW);
    }

    default Optional<Cart> getActiveCartForUser(User user) {
        return this.getFirstByUserAndStatus(user, CartStatus.NEW
        );
    }
}
