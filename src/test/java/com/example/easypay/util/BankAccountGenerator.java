package com.example.easypay.util;

import com.example.easypay.model.entity.BankAccount;
import com.example.easypay.model.entity.User;
import com.example.easypay.model.enums.CurrencyType;
import java.math.BigDecimal;

public final class BankAccountGenerator {

  private static final BigDecimal BALANCE = BigDecimal.valueOf(123);

  private BankAccountGenerator() {}

  public static BankAccount generateBankAccountWithUser(User user) {
    return new BankAccount(BALANCE, CurrencyType.DOLLAR.getCurrency(), user);
  }

  public static BankAccount generateBankAccountWithBalance() {
    return new BankAccount(BALANCE);
  }

  public static BankAccount generateBankAccountWithBalance(Long id) {
    return new BankAccount(id);
  }

  public static BankAccount generateBankAccountWithBalance(BigDecimal balance) {
    return new BankAccount(balance);
  }
}
