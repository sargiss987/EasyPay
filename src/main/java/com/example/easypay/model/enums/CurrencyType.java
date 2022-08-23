package com.example.easypay.model.enums;

public enum CurrencyType {
    DOLLAR("Dollar"), EURO("Euro");

    private final String currency;

    CurrencyType(String currency){
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }
}
