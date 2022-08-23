package com.example.easypay.service;

import com.example.easypay.model.dto.UserLoginDto;
import com.example.easypay.model.dto.UserRegistrationDto;
import com.example.easypay.model.entity.User;

public interface AuthService {

  User register(UserRegistrationDto userRegistrationDto);

  void authenticateLoggedUser(UserLoginDto userLoginDto);

  User getLoggedUserByEmail(String email);
}
