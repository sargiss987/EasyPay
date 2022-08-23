package com.example.easypay.utils.validator;

import static com.example.easypay.constants.UserRegistrationValidatorConstants.EMAIL_MESSAGE;
import static com.example.easypay.constants.UserRegistrationValidatorConstants.FIRST_NAME_MESSAGE;
import static com.example.easypay.constants.UserRegistrationValidatorConstants.LAST_NAME_MESSAGE;
import static com.example.easypay.constants.UserRegistrationValidatorConstants.PASSWORD_MESSAGE;

import com.example.easypay.model.dto.UserRegistrationDto;
import com.example.easypay.model.validation.UserRegistrationValidationErrors;

public final class UserRegistrationValidator {

  private UserRegistrationValidator() {}

  public static UserRegistrationValidationErrors validate(UserRegistrationDto userRegistrationDto) {
    UserRegistrationValidationErrors userRegistrationValidationErrors =
        new UserRegistrationValidationErrors();
    validateFirstName(userRegistrationDto, userRegistrationValidationErrors);
    validateLastName(userRegistrationDto, userRegistrationValidationErrors);
    validateEmail(userRegistrationDto, userRegistrationValidationErrors);
    validatePassword(userRegistrationDto, userRegistrationValidationErrors);
    return userRegistrationValidationErrors;
  }

  private static void validateFirstName(
      UserRegistrationDto userRegistrationDto,
      UserRegistrationValidationErrors userRegistrationValidationErrors) {
    if (isEmptyOrLengthGraterThenMax(userRegistrationDto.getFirstName(), 30)) {
      userRegistrationValidationErrors.setFirstNameError(FIRST_NAME_MESSAGE);
      userRegistrationValidationErrors.setHasErrors(true);
    }
  }

  private static void validateLastName(
      UserRegistrationDto userRegistrationDto,
      UserRegistrationValidationErrors userRegistrationValidationErrors) {
    if (isEmptyOrLengthGraterThenMax(userRegistrationDto.getLastName(), 30)) {
      userRegistrationValidationErrors.setLastNameError(LAST_NAME_MESSAGE);
      userRegistrationValidationErrors.setHasErrors(true);
    }
  }

  private static void validateEmail(
      UserRegistrationDto userRegistrationDto,
      UserRegistrationValidationErrors userRegistrationValidationErrors) {
    if (isEmptyOrLengthGraterThenMax(userRegistrationDto.getEmail(), 30)) {
      userRegistrationValidationErrors.setEmailError(EMAIL_MESSAGE);
      userRegistrationValidationErrors.setHasErrors(true);
    }
  }

  private static void validatePassword(
      UserRegistrationDto userRegistrationDto,
      UserRegistrationValidationErrors userRegistrationValidationErrors) {
    if (isEmptyOrLengthGraterThenMax(userRegistrationDto.getPassword(), 30)) {
      userRegistrationValidationErrors.setPasswordError(PASSWORD_MESSAGE);
      userRegistrationValidationErrors.setHasErrors(true);
    }
  }

  private static boolean isEmptyOrLengthGraterThenMax(String field, int max) {
    return FormValidator.isEmpty(field) || FormValidator.maxLength(field, max);
  }
}
