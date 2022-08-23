package com.example.easypay.utils.validator;

import static com.example.easypay.constants.BankAccountValidatorConstants.BALANCE_MESSAGE;
import static com.example.easypay.constants.BankAccountValidatorConstants.CURRENCY_MESSAGE;

import com.example.easypay.model.dto.BankAccountDto;
import com.example.easypay.model.validation.BankAccountValidationErrors;

public final class BankAccountValidator {

  private BankAccountValidator() {}

  public static BankAccountValidationErrors validate(BankAccountDto bankAccountDto) {
    BankAccountValidationErrors validationErrors = new BankAccountValidationErrors();
    validateBalance(bankAccountDto, validationErrors);
    validateCurrency(bankAccountDto, validationErrors);
    return validationErrors;
  }

  private static void validateCurrency(
      BankAccountDto bankAccountDto, BankAccountValidationErrors validationErrors) {
    if (FormValidator.isEmpty(bankAccountDto.getBalance())) {
      validationErrors.setHasErrors(true);
      validationErrors.setBalanceError(BALANCE_MESSAGE);
    }
  }

  private static void validateBalance(
      BankAccountDto bankAccountDto, BankAccountValidationErrors validationErrors) {
    if (FormValidator.isEmpty(bankAccountDto.getCurrency())
        || FormValidator.isExistingCurrency(bankAccountDto.getCurrency())) {
      validationErrors.setHasErrors(true);
      validationErrors.setCurrencyError(CURRENCY_MESSAGE);
    }
  }
}
