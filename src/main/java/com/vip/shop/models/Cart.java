package com.vip.shop.models;

import com.vip.shop.enums.CartStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    private LocalDateTime orderingDate;
}
