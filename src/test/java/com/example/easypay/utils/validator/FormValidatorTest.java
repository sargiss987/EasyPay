package com.example.easypay.utils.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.easypay.model.enums.CurrencyType;
import org.junit.jupiter.api.Test;

class FormValidatorTest {

  @Test
  void isEmpty_withEmptyField_returnTrue() {
    String target = "";
    assertTrue(FormValidator.isEmpty(target));
  }

  @Test
  void maxLength_WithExceededLength_returnTrue() {
    String target = "asdfghjkjk";
    assertTrue(FormValidator.maxLength(target, 3));
  }

  @Test
  void maxLength_WithNullString_returnTrue() {
    assertTrue(FormValidator.maxLength(null, 3));
  }

  @Test
  void isExistingCurrency_withCorrectType_returnFalse(){
    String dollar = "Dollar";
    String euro = "Euro";
    assertFalse(FormValidator.isExistingCurrency(dollar));
    assertFalse(FormValidator.isExistingCurrency(euro));
  }

  @Test
  void isExistingCurrency_withIncorrectType_returnTrue(){
    String dram = "Dram";
    assertTrue(FormValidator.isExistingCurrency(dram));
  }
}
