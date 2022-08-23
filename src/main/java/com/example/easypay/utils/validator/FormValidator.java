package com.example.easypay.utils.validator;

import com.example.easypay.model.enums.CurrencyType;
import java.math.BigDecimal;

public final class FormValidator {

  private FormValidator() {}

  public static boolean isEmpty(String field) {
    return field == null || field.length() == 0;
  }

  public static boolean isEmpty(BigDecimal field) {
    return field == null;
  }

  public static boolean maxLength(String field, int max) {
    return field == null || field.trim().length() > max;
  }

  public static boolean isExistingCurrency(String field) {
    return (!field.equals(CurrencyType.DOLLAR.getCurrency()))
        && (!field.equals(CurrencyType.EURO.getCurrency()));
  }
}
