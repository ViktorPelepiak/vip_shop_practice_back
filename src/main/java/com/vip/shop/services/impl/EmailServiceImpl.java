package com.vip.shop.services.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.vip.shop.enums.EmailType;
import com.vip.shop.exceptions.EmailException;
import com.vip.shop.mail.EmailInstance;
import com.vip.shop.mail.MailGenerator;
import com.vip.shop.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@PropertySource("/email.properties")
public class EmailServiceImpl implements EmailService {

    private final Environment environment;
    private final Map<EmailType, MailGenerator> mailGenerators;

    @Autowired
    EmailServiceImpl(Environment environment, List<MailGenerator> mailGenerators) {
        this.environment = environment;
        this.mailGenerators = mailGenerators.stream()
                .collect(Collectors.toMap(
                        MailGenerator::getEmailType,
                        value -> value));
    }

    public void sendEmail(EmailType type, String emailTo, Map<String, Object> parameters) {
        MailGenerator mailGenerator = mailGenerators.get(type);
        if (mailGenerator == null) {
            throw new UnsupportedOperationException(
                    String.format("Emails with type <%s> are not supported", type.name()));
        }
        EmailInstance emailInstance = mailGenerator.generate(parameters);

        Mail mail = new Mail(
                emailInstance.getFrom(),
                emailInstance.getSubject(),
                new Email(emailTo),
                emailInstance.getContent());

        SendGrid sendGrid = new SendGrid(environment.getProperty("sendgrid.api.key"));
        Request request = new Request();

        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
        } catch (IOException e) {
            throw new EmailException("Error during email sending");
        }
    }
}
