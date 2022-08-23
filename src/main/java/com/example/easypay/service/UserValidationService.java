package com.example.easypay.service;

import com.example.easypay.model.dto.BankAccountDto;
import com.example.easypay.model.validation.BankAccountValidationErrors;
import com.example.easypay.model.dto.UserRegistrationDto;
import com.example.easypay.model.validation.UserRegistrationValidationErrors;

public interface UserValidationService {

  UserRegistrationValidationErrors validateUserRegistrationDto(
      UserRegistrationDto userRegistrationDto);

  BankAccountValidationErrors validateBankAccountDto(BankAccountDto bankAccountDto);
}
