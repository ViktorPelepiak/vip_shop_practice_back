package com.vip.shop.controllers;

import com.vip.shop.dto.DebtorDto;
import com.vip.shop.rest.GenericResponse;
import com.vip.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("debtors")
    public GenericResponse<List<DebtorDto>> getDebtors() {
        return GenericResponse.of(
                userService.getDebtors().stream()
                .map(DebtorDto::toDto)
                .collect(Collectors.toList())
        );
    }

    @PutMapping("block/{userId}")
    public GenericResponse<DebtorDto> blockUser(@PathVariable Long userId) {
        return GenericResponse.of(DebtorDto.toDto(userService.blockUserById(userId)));
    }

    @PutMapping("unblock/{userId}")
    public GenericResponse<DebtorDto> unblockUser(@PathVariable Long userId) {
        return GenericResponse.of(DebtorDto.toDto(userService.unblockUserById(userId)));
    }
}
