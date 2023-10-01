package com.vip.shop.services.impl;

import com.vip.shop.exceptions.ElementNotFoundException;
import com.vip.shop.exceptions.TokenExpirationException;
import com.vip.shop.models.User;
import com.vip.shop.models.VerificationToken;
import com.vip.shop.repository.VerificationTokenRepository;
import com.vip.shop.services.UserService;
import com.vip.shop.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserService userService;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository, UserService userService) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userService = userService;
    }

    @Override
    public VerificationToken generate(User user) {
        return verificationTokenRepository.save(
                new VerificationToken()
                        .setUser(user)
                        .setToken(UUID.randomUUID().toString())
                        .setExpirationDate(VerificationToken.calculateExpiryDate())
        );
    }

    @Override
    public boolean verify(String token) throws TokenExpirationException, ElementNotFoundException {
        VerificationToken verificationToken = verificationTokenRepository.getFirstByToken(token);
        if (verificationToken == null) {
            throw new ElementNotFoundException(String.format("Token \"%s\" not found", token));
        }
        if (verificationToken.getExpirationDate().after(new Date())) {
            userService.activateUserById(verificationToken.getUser().getId());
            verificationTokenRepository.delete(verificationToken);
            return true;
        }
        throw new TokenExpirationException();
    }

    @Override
    public VerificationToken getByToken(String token) {
        return verificationTokenRepository.getFirstByToken(token);
    }

    @Override
    public boolean remove(VerificationToken token) {
        verificationTokenRepository.delete(token);

        return true;
    }
}
