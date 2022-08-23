package com.example.easypay.util;

import com.example.easypay.model.security.BankUserDetails;

public final class BankUserDetailsGenerator {

  private BankUserDetailsGenerator() {}

  public static BankUserDetails generateBankUserDetails() {
    return new BankUserDetails(UserGenerator.generateBankUser());
  }
}
