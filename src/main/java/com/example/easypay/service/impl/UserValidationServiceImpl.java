package com.example.easypay.service.impl;

import com.example.easypay.model.dto.BankAccountDto;
import com.example.easypay.model.validation.BankAccountValidationErrors;
import com.example.easypay.model.dto.UserRegistrationDto;
import com.example.easypay.model.validation.UserRegistrationValidationErrors;
import com.example.easypay.service.UserValidationService;
import com.example.easypay.utils.validator.BankAccountValidator;
import com.example.easypay.utils.validator.UserRegistrationValidator;
import org.springframework.stereotype.Service;

@Service
public class UserValidationServiceImpl implements UserValidationService {

  @Override
  public UserRegistrationValidationErrors validateUserRegistrationDto(
      UserRegistrationDto userRegistrationDto) {
    return UserRegistrationValidator.validate(userRegistrationDto);
  }

  @Override
  public BankAccountValidationErrors validateBankAccountDto(BankAccountDto bankAccountDto) {
    return BankAccountValidator.validate(bankAccountDto);
  }
}
