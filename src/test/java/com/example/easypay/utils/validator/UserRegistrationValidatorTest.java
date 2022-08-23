package com.example.easypay.utils.validator;

import static com.example.easypay.constants.UserRegistrationValidatorConstants.EMAIL_MESSAGE;
import static com.example.easypay.constants.UserRegistrationValidatorConstants.FIRST_NAME_MESSAGE;
import static com.example.easypay.constants.UserRegistrationValidatorConstants.LAST_NAME_MESSAGE;
import static com.example.easypay.constants.UserRegistrationValidatorConstants.PASSWORD_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.easypay.model.validation.UserRegistrationValidationErrors;
import com.example.easypay.util.UserRegistrationDtoGenerator;
import org.junit.jupiter.api.Test;

class UserRegistrationValidatorTest {

  @Test
  void validate_withEmptyFirstname_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithEmptyFirstname());

    assertTrue(validationErrors.hasErrors());
    assertEquals(FIRST_NAME_MESSAGE, validationErrors.getFirstNameError());
  }

  @Test
  void validate_withTooLongFirstname_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithLongFirstname());

    assertTrue(validationErrors.hasErrors());
    assertEquals(FIRST_NAME_MESSAGE, validationErrors.getFirstNameError());
  }

  @Test
  void validate_withNullFirstname_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithNullFirstname());

    assertTrue(validationErrors.hasErrors());
    assertEquals(FIRST_NAME_MESSAGE, validationErrors.getFirstNameError());
  }

  @Test
  void validate_withEmptyLastname_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithEmptyLastname());

    assertTrue(validationErrors.hasErrors());
    assertEquals(LAST_NAME_MESSAGE, validationErrors.getLastNameError());
  }

  @Test
  void validate_withTooLongLastname_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithLongLastname());

    assertTrue(validationErrors.hasErrors());
    assertEquals(LAST_NAME_MESSAGE, validationErrors.getLastNameError());
  }

  @Test
  void validate_withNullLastname_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithNullLastname());

    assertTrue(validationErrors.hasErrors());
    assertEquals(LAST_NAME_MESSAGE, validationErrors.getLastNameError());
  }

  @Test
  void validate_withEmptyEmail_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithEmptyEmail());

    assertTrue(validationErrors.hasErrors());
    assertEquals(EMAIL_MESSAGE, validationErrors.getEmailError());
  }

  @Test
  void validate_withTooLongEmail_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithLongEmail());

    assertTrue(validationErrors.hasErrors());
    assertEquals(EMAIL_MESSAGE, validationErrors.getEmailError());
  }

  @Test
  void validate_withNullEmail_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithNullEmail());

    assertTrue(validationErrors.hasErrors());
    assertEquals(EMAIL_MESSAGE, validationErrors.getEmailError());
  }

  @Test
  void validate_withEmptyPassword_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithEmptyPassword());

    assertTrue(validationErrors.hasErrors());
    assertEquals(PASSWORD_MESSAGE, validationErrors.getPasswordError());
  }

  @Test
  void validate_withTooLongPassword_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithLongPassword());

    assertTrue(validationErrors.hasErrors());
    assertEquals(PASSWORD_MESSAGE, validationErrors.getPasswordError());
  }

  @Test
  void validate_withNullPassword_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDtoWithNullPassword());

    assertTrue(validationErrors.hasErrors());
    assertEquals(PASSWORD_MESSAGE, validationErrors.getPasswordError());
  }

  @Test
  void validate_withWithCorrectUserRegistrationDto_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDto());

    assertFalse(validationErrors.hasErrors());
  }

  @Test
  void validate_returnValidationErrors() {
    UserRegistrationValidationErrors validationErrors =
        UserRegistrationValidator.validate(
            UserRegistrationDtoGenerator.generateUserRegistrationDto());

    assertFalse(validationErrors.hasErrors());
  }
}
