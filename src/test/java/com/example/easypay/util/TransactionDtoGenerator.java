package com.example.easypay.util;

import com.example.easypay.model.dto.TransactionDto;
import java.math.BigDecimal;

public final class TransactionDtoGenerator {

  private TransactionDtoGenerator() {}

  private static final String DUMMY_STRING = "dummy";
  private static final BigDecimal AMOUNT_ZERO = BigDecimal.valueOf(0.0);
  private static final BigDecimal NEGATIVE_AMOUNT = BigDecimal.valueOf(-100.0);
  private static final BigDecimal DUMMY_AMOUNT = BigDecimal.valueOf(100.00);
  private static final BigDecimal AMOUNT_NULL = null;
  private static final Long DUMMY_ACCOUNT_ID = 1L;

  public static TransactionDto generateTransactionDtoWithAmountEqualsZero() {
    return new TransactionDto(DUMMY_STRING, DUMMY_ACCOUNT_ID, AMOUNT_ZERO);
  }

  public static TransactionDto generateTransactionDtoWithNullAmount() {
    return new TransactionDto(DUMMY_STRING, DUMMY_ACCOUNT_ID, AMOUNT_NULL);
  }

  public static TransactionDto generateTransactionDto() {
    return new TransactionDto(DUMMY_STRING, DUMMY_ACCOUNT_ID, DUMMY_AMOUNT);
  }

  public static TransactionDto generateTransactionDtoWithNegativeAmount() {
    return new TransactionDto(DUMMY_STRING, DUMMY_ACCOUNT_ID, NEGATIVE_AMOUNT);
  }
}
