package com.vip.shop.mail;

import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class EmailInstance {
    private Email from;
    private String subject;
    private Content content;

    public Email getFrom() {
        return from;
    }

    public EmailInstance setFrom(Email from) {
        this.from = from;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public EmailInstance setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Content getContent() {
        return content;
    }

    public EmailInstance setContent(Content content) {
        this.content = content;
        return this;
    }
}
