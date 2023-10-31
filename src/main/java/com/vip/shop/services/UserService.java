package com.vip.shop.services;

import com.vip.shop.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface UserService extends UserDetailsService {
    void activateUserById(Long id);

    List<User> getDebtors();

    User blockUserById(Long userId);

    User unblockUserById(Long userId);
}
