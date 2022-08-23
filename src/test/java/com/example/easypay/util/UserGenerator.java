package com.example.easypay.util;

import com.example.easypay.model.entity.Role;
import com.example.easypay.model.entity.User;

public final class UserGenerator {

  private UserGenerator() {}

  private static final String DUMMY_STRING = "dummy";

  public static User generateBankUser() {
    return new User(DUMMY_STRING, DUMMY_STRING, DUMMY_STRING, DUMMY_STRING, Role.isBankClient());
  }

  public static User generateBankUser(String email) {
    return new User(DUMMY_STRING, DUMMY_STRING, DUMMY_STRING, email, Role.isBankClient());
  }

  public static User generateBankUserWithBankAccount() {
    User user = generateBankUser();
    user.getAccounts().add(BankAccountGenerator.generateBankAccountWithUser(user));
    return user;
  }

  public static User generateBankUserWithBankAccount(Long id) {
    User user = generateBankUser();
    user.getAccounts().add(BankAccountGenerator.generateBankAccountWithBalance(id));
    return user;
  }
}
