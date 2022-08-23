package com.example.easypay.utils.validator;

import static com.example.easypay.constants.BankAccountValidatorConstants.BALANCE_MESSAGE;
import static com.example.easypay.constants.BankAccountValidatorConstants.CURRENCY_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.easypay.model.validation.BankAccountValidationErrors;
import com.example.easypay.util.BankAccountDtoGenerator;
import org.junit.jupiter.api.Test;

class BankAccountValidatorTest {

  @Test
  void validate_withNullBalance_returnValidationErrors() {
    BankAccountValidationErrors validationErrors =
        BankAccountValidator.validate(
            BankAccountDtoGenerator.generateBankAccountDtoWithNullBalance());

    assertTrue(validationErrors.hasErrors());
    assertEquals(BALANCE_MESSAGE, validationErrors.getBalanceError());
  }

  @Test
  void validate_withNullCurrency_returnValidationErrors() {
    BankAccountValidationErrors validationErrors =
        BankAccountValidator.validate(
            BankAccountDtoGenerator.generateBankAccountDtoWithNullCurrency());

    assertTrue(validationErrors.hasErrors());
    assertEquals(CURRENCY_MESSAGE, validationErrors.getCurrencyError());
  }

  @Test
  void validate_withIncorrectCurrencyType_returnValidationErrors() {
    BankAccountValidationErrors validationErrors =
        BankAccountValidator.validate(
            BankAccountDtoGenerator.generateBankAccountDtoWithIncorrectCurrency());

    assertTrue(validationErrors.hasErrors());
    assertEquals(CURRENCY_MESSAGE, validationErrors.getCurrencyError());
  }

  @Test
  void validate_withCorrectCredentials_returnValidationErrors() {
    BankAccountValidationErrors validationErrors =
        BankAccountValidator.validate(BankAccountDtoGenerator.generateBankAccountDto());

    assertFalse(validationErrors.hasErrors());
  }
}
