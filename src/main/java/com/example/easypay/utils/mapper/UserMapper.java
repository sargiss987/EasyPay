package com.example.easypay.utils.mapper;

import com.example.easypay.model.dto.UserRegistrationDto;
import com.example.easypay.model.entity.Role;
import com.example.easypay.model.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class UserMapper {

  private UserMapper() {}

  public static User ToUser(
      UserRegistrationDto userRegistrationDto, BCryptPasswordEncoder passwordEncoder) {
    return new User(
        userRegistrationDto.getFirstName(),
        userRegistrationDto.getLastName(),
        passwordEncoder.encode(userRegistrationDto.getPassword()),
        userRegistrationDto.getEmail(),
        Role.isBankClient());
  }
}
