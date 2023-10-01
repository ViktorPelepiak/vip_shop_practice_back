package com.vip.shop.services.impl;

import com.vip.shop.dto.UserLoginDto;
import com.vip.shop.dto.UserRegistrationDto;
import com.vip.shop.enums.EmailType;
import com.vip.shop.enums.UserRole;
import com.vip.shop.exceptions.ElementNotFoundException;
import com.vip.shop.exceptions.TokenExpirationException;
import com.vip.shop.mail.MailGenerator;
import com.vip.shop.models.User;
import com.vip.shop.models.VerificationToken;
import com.vip.shop.repository.UserRepository;
import com.vip.shop.services.EmailService;
import com.vip.shop.services.SecurityService;
import com.vip.shop.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

@Service
@Transactional
@PropertySource("/frontend.properties")
public class SecurityServiceImpl implements SecurityService {

    private final Environment environment;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    @Autowired
    public SecurityServiceImpl(Environment environment, AuthenticationManager authenticationManager,
                               UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                               VerificationTokenService verificationTokenService, EmailService emailService) {
        this.environment = environment;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
    }

    @Override
    public User login(UserLoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationTokenRequest = new
                UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationTokenRequest);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            userRepository.save(user);

            return user;
        } catch (BadCredentialsException | InternalAuthenticationServiceException ex) {
            return null;
        }
    }

    @Override
    public User registration(UserRegistrationDto registrationDto) {
        User user = userRepository.save(new User()
                .setUsername(registrationDto.getUsername()))
                .setEmail(registrationDto.getEmail())
                .setPassword(passwordEncoder.encode(registrationDto.getPassword()))
                .setRoles(new HashSet<>(Collections.singletonList(UserRole.CLIENT)))
                .setEnabled(false);

        if (user != null) {
            String token = verificationTokenService.generate(user).getToken();

            emailService.sendEmail(EmailType.USER_VERIFICATION, user.getEmail(),
                    Map.of(
                            MailGenerator.USERNAME, user.getUsername(),
                            MailGenerator.VERIFICATION_TOKEN, environment.getProperty("frontend.url") + "/user/verification?token=" + token
                    ));
        }

        return user;
    }

    @Override
    public boolean verifyToken(String token) throws TokenExpirationException, ElementNotFoundException {
        return verificationTokenService.verify(token);
    }

    @Override
    public boolean updateToken(String token) {
        VerificationToken expiredToken = verificationTokenService.getByToken(token);

        String freshToken = verificationTokenService.generate(expiredToken.getUser()).getToken();

        emailService.sendEmail(EmailType.USER_VERIFICATION, expiredToken.getUser().getEmail(),
                Map.of(
                        MailGenerator.USERNAME, expiredToken.getUser().getUsername(),
                        MailGenerator.VERIFICATION_TOKEN, environment.getProperty("frontend.url") + "/user/verification?token=" + freshToken
                ));

        return verificationTokenService.remove(expiredToken);
    }
}
