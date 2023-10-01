package com.vip.shop.dto;

import com.vip.shop.models.User;

import java.util.stream.Collectors;

public class UserResponseDto {
    private String username;
    private String email;
    private String role;

    public String getUsername() {
        return username;
    }

    public UserResponseDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserResponseDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserResponseDto setRole(String role) {
        this.role = role;
        return this;
    }

    public static UserResponseDto fromUser(User user) {
        return new UserResponseDto()
                .setEmail(user.getEmail())
                .setUsername(user.getUsername())
                .setRole(user.getRoles().stream().map(Enum::toString).collect(Collectors.joining(",")));
    }
}
