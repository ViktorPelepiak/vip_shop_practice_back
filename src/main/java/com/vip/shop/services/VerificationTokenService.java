package com.vip.shop.services;

import com.vip.shop.exceptions.ElementNotFoundException;
import com.vip.shop.exceptions.TokenExpirationException;
import com.vip.shop.models.User;
import com.vip.shop.models.VerificationToken;
import org.springframework.stereotype.Service;

@Service
public interface VerificationTokenService {

    VerificationToken generate(User user);

    boolean verify(String token) throws TokenExpirationException, ElementNotFoundException;

    VerificationToken getByToken(String token);

    boolean remove(VerificationToken token);
}
