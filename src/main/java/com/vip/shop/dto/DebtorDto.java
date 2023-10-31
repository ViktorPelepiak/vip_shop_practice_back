package com.vip.shop.dto;

import com.vip.shop.models.User;

public class DebtorDto {
    private Long id;
    private String email;
    private String username;
    private boolean blocked;

    public Long getId() {
        return id;
    }

    public DebtorDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public DebtorDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public DebtorDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public DebtorDto setBlocked(boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    public static DebtorDto toDto(User user) {
        return new DebtorDto()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setUsername(user.getUsername())
                .setBlocked(user.isBlocked());
    }
}
