package com.vip.shop.mail.strategies;

import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.vip.shop.enums.EmailType;
import com.vip.shop.mail.EmailInstance;
import com.vip.shop.mail.MailGenerator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("USER_VERIFICATION")
public class UserValidationMailGenerator implements MailGenerator {
    private static final String FROM_EMAIL = "viktorpelepiak@gmail.com";

    @Override
    public EmailType getEmailType() {
        return EmailType.USER_VERIFICATION;
    }

    @Override
    public EmailInstance generate(Map<String, Object> parameters) {
        return new EmailInstance()
                .setFrom(new Email(FROM_EMAIL))
                .setSubject("ViP Shop account verification")
                .setContent(new Content("text/html", generateMailBody(parameters)));
    }

    private String generateMailBody(Map<String, Object> parameters){
        return new StringBuilder()
                .append("<html>")
                .append("<body>")
                .append(String.format("<h3>Hallo, %s!</h3>", parameters.get(MailGenerator.USERNAME)))
                .append("<br>")
                .append("<p>For completing registration please follow the ")
                .append(String.format("<a href=\"%s\">link</a>", parameters.get(MailGenerator.VERIFICATION_TOKEN)))
                .append(".</p>")
                .append("</body>")
                .append("</html>")
                .toString();
    }
}
