package com.vip.shop.repository;

import com.vip.shop.jpa.CartItemJpaId;
import com.vip.shop.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemJpaId> {
}
