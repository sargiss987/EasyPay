package com.example.easypay.util;

import com.example.easypay.model.dto.UserRegistrationDto;

public final class UserRegistrationDtoGenerator {

  private static final String DUMMY_STRING = "dummy";
  private static final String LONG_STRING = "qazwsxedcrfvtgbyhnujmikqazwsxedc";
  private static final String EMPTY_STRING = "";
  private static final String NULL_STRING = null;

  private UserRegistrationDtoGenerator() {}

  public static UserRegistrationDto generateUserRegistrationDto() {
    return new UserRegistrationDto(DUMMY_STRING, DUMMY_STRING, DUMMY_STRING, DUMMY_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithEmptyFirstname() {
    return new UserRegistrationDto(EMPTY_STRING, DUMMY_STRING, DUMMY_STRING, DUMMY_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithEmptyLastname() {
    return new UserRegistrationDto(DUMMY_STRING, EMPTY_STRING, DUMMY_STRING, DUMMY_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithEmptyEmail() {
    return new UserRegistrationDto(DUMMY_STRING, DUMMY_STRING, EMPTY_STRING, DUMMY_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithEmptyPassword() {
    return new UserRegistrationDto(DUMMY_STRING, DUMMY_STRING, DUMMY_STRING, EMPTY_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithLongFirstname() {
    return new UserRegistrationDto(LONG_STRING, DUMMY_STRING, DUMMY_STRING, DUMMY_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithLongLastname() {
    return new UserRegistrationDto(DUMMY_STRING, LONG_STRING, DUMMY_STRING, DUMMY_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithLongEmail() {
    return new UserRegistrationDto(DUMMY_STRING, DUMMY_STRING, LONG_STRING, DUMMY_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithLongPassword() {
    return new UserRegistrationDto(DUMMY_STRING, DUMMY_STRING, DUMMY_STRING, LONG_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithNullFirstname() {
    return new UserRegistrationDto(NULL_STRING, DUMMY_STRING, DUMMY_STRING, DUMMY_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithNullLastname() {
    return new UserRegistrationDto(DUMMY_STRING, NULL_STRING, DUMMY_STRING, DUMMY_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithNullEmail() {
    return new UserRegistrationDto(DUMMY_STRING, DUMMY_STRING, NULL_STRING, DUMMY_STRING);
  }

  public static UserRegistrationDto generateUserRegistrationDtoWithNullPassword() {
    return new UserRegistrationDto(DUMMY_STRING, DUMMY_STRING, DUMMY_STRING, NULL_STRING);
  }
}
