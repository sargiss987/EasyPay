package com.example.easypay.utils.mapper;

import com.example.easypay.model.dto.BankAccountDto;
import com.example.easypay.model.entity.BankAccount;
import com.example.easypay.model.entity.User;

public final class BankAccountMapper {

  private BankAccountMapper() {}

  public static BankAccount toBankAccount(BankAccountDto bankAccountDto, User user) {
    return new BankAccount(bankAccountDto.getBalance(), bankAccountDto.getCurrency(), user);
  }
}
