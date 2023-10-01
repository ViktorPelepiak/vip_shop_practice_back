package com.vip.shop.services;

import com.vip.shop.enums.EmailType;

import java.util.Map;

public interface EmailService {
    void sendEmail(EmailType type, String emailTo, Map<String, Object> parameters);
}
