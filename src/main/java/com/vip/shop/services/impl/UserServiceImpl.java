package com.vip.shop.services.impl;

import com.vip.shop.models.User;
import com.vip.shop.repository.UserRepository;
import com.vip.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void activateUserById(Long userId) {
        userRepository.save(
                userRepository.getReferenceById(userId)
                        .setEnabled(true)
        );
    }

    @Override
    public List<User> getDebtors() {
        return userRepository.getDebtors();
    }

    @Override
    public User blockUserById(Long userId) {
        return userRepository.save(
                userRepository
                        .findById(userId)
                        .orElseThrow(IllegalArgumentException::new)
                .setBlocked(true)
        );
    }

    @Override
    public User unblockUserById(Long userId) {
        return userRepository.save(
                userRepository
                        .findById(userId)
                        .orElseThrow(IllegalArgumentException::new)
                        .setBlocked(false)
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username).orElse(null);
    }
}
