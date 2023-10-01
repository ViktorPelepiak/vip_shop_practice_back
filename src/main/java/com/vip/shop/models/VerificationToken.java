package com.vip.shop.models;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "verification_tokens")
public class VerificationToken {

    @Value("token.expiration.in.hours")
    private final static int EXPIRATION_IN_HOURS = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    public static Date calculateExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.HOUR, EXPIRATION_IN_HOURS);
        return new Date(cal.getTime().getTime());
    }

    public Long getId() {
        return id;
    }

    public VerificationToken setId(Long id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public VerificationToken setToken(String token) {
        this.token = token;
        return this;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public VerificationToken setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public User getUser() {
        return user;
    }

    public VerificationToken setUser(User user) {
        this.user = user;
        return this;
    }
}
