package com.example.easypay.model.dto;

import java.math.BigDecimal;

public class BankAccountDto {

    private BigDecimal balance;
    private String currency;

    public BankAccountDto() {
    }

    public BankAccountDto(BigDecimal balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public static BankAccountDto getInstance(){
        return new BankAccountDto();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
