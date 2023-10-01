package com.vip.shop.services;

import com.vip.shop.dto.UserLoginDto;
import com.vip.shop.dto.UserRegistrationDto;
import com.vip.shop.exceptions.ElementNotFoundException;
import com.vip.shop.exceptions.TokenExpirationException;
import com.vip.shop.models.User;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
    User login(UserLoginDto userLoginDto);

    User registration(UserRegistrationDto userRegistrationDto);

    boolean verifyToken(String token) throws TokenExpirationException, ElementNotFoundException;

    boolean updateToken(String token);
}
