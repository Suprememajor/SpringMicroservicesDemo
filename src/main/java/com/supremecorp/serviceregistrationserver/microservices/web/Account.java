package com.supremecorp.serviceregistrationserver.microservices.web;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String number;
    protected String owner;
    protected BigDecimal balance;

    public Account() {
        balance = BigDecimal.ZERO;
    }

    public BigDecimal getBalance(BigDecimal amount) {
        return balance.setScale(2, RoundingMode.HALF_EVEN);
    }

    protected void setBalance(BigDecimal value) {
        balance = value;
        balance.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", owner='" + owner + '\'' +
                ", balance=" + balance +
                '}';
    }
}
