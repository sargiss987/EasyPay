package com.example.easypay.util;

import com.example.easypay.model.dto.UserLoginDto;

public final class UserLoginDtoGenerator {

  private static final String DUMMY_STRING = "dummy";

  private UserLoginDtoGenerator() {}

  public static UserLoginDto generateUserLoginDto() {
    return new UserLoginDto(DUMMY_STRING, DUMMY_STRING);
  }
}
