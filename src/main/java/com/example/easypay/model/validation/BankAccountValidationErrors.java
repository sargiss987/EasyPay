package com.example.easypay.model.validation;

public class BankAccountValidationErrors {

    private String balanceError;
    private String currencyError;
    private boolean hasErrors;

    public String getBalanceError() {
        return balanceError;
    }

    public void setBalanceError(String balanceError) {
        this.balanceError = balanceError;
    }

    public String getCurrencyError() {
        return currencyError;
    }

    public void setCurrencyError(String currencyError) {
        this.currencyError = currencyError;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }
}
