package com.example.easypay.utils.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.easypay.model.entity.User;
import com.example.easypay.util.UserGenerator;
import com.example.easypay.util.UserRegistrationDtoGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserMapperTest {

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Test
  void userRegistrationDtoToUser_returnUser() {
    User bankUser = UserGenerator.generateBankUser();
    User userGeneratedByMapper =
        UserMapper.ToUser(
            UserRegistrationDtoGenerator.generateUserRegistrationDto(), passwordEncoder);

    assertEquals(bankUser, userGeneratedByMapper);
  }
}
