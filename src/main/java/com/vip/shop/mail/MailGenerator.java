package com.vip.shop.mail;

import com.vip.shop.enums.EmailType;

import java.util.Map;

public interface MailGenerator {
    String USERNAME = "username";
    String VERIFICATION_TOKEN = "verificationToken";

    EmailType getEmailType();
    EmailInstance generate(Map<String, Object> parameters);
}
