package com.example.easypay.util;

import com.example.easypay.model.dto.BankAccountDto;
import com.example.easypay.model.enums.CurrencyType;
import java.math.BigDecimal;

public final class BankAccountDtoGenerator {

  private static final BigDecimal NULL_BALANCE = null;
  private static final BigDecimal BALANCE = BigDecimal.valueOf(123.00003);
  private static final String NULL_CURRENCY = null;
  private static final String INCORRECT_CURRENCY = "Dram";

  private BankAccountDtoGenerator() {}

  public static BankAccountDto generateBankAccountDtoWithNullBalance() {
    return new BankAccountDto(NULL_BALANCE, CurrencyType.DOLLAR.getCurrency());
  }

  public static BankAccountDto generateBankAccountDtoWithNullCurrency() {
    return new BankAccountDto(BALANCE, NULL_CURRENCY);
  }

  public static BankAccountDto generateBankAccountDtoWithIncorrectCurrency() {
    return new BankAccountDto(BALANCE, INCORRECT_CURRENCY);
  }

  public static BankAccountDto generateBankAccountDto() {
    return new BankAccountDto(BALANCE, CurrencyType.EURO.getCurrency());
  }
}
