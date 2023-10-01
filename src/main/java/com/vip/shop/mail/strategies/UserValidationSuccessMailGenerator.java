package com.vip.shop.mail.strategies;

import com.vip.shop.enums.EmailType;
import com.vip.shop.mail.EmailInstance;
import com.vip.shop.mail.MailGenerator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("USER_VERIFICATION_SUCCESS")
public class UserValidationSuccessMailGenerator implements MailGenerator {
    @Override
    public EmailType getEmailType() {
        return EmailType.USER_VERIFICATION_SUCCESS;
    }

    @Override
    public EmailInstance generate(Map<String, Object> parameters) {
        return null;
    }
}
