package com.example.easypay.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.easypay.repository.UserRepository;
import com.example.easypay.util.BankUserAuthenticator;
import com.example.easypay.util.TransactionDtoGenerator;
import com.example.easypay.util.UserGenerator;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionValidationServiceImplTest {

  @Mock UserRepository userRepository;

  private TransactionValidationServiceImpl validationService;

  @BeforeEach
  void setUp() {
    validationService = new TransactionValidationServiceImpl(userRepository);
  }

  @Test
  void validateTransaction_withCorrectCredentials_returnTrue() {
    Long correctId = 1L;
    BankUserAuthenticator.setAuthentication();
    when(userRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(UserGenerator.generateBankUserWithBankAccount(correctId)));

    assertTrue(
        validationService.validateTransaction(TransactionDtoGenerator.generateTransactionDto()));
  }

  @Test
  void validateTransaction_withAmountEqualsZero_returnFalse() {
    assertFalse(
        validationService.validateTransaction(
            TransactionDtoGenerator.generateTransactionDtoWithAmountEqualsZero()));
  }

  @Test
  void validateTransaction_withNegativeAmount_returnFalse() {
    assertFalse(
            validationService.validateTransaction(
                    TransactionDtoGenerator.generateTransactionDtoWithNegativeAmount()));
  }

  @Test
  void validateTransaction_withNullAmount_returnFalse() {
    assertFalse(
        validationService.validateTransaction(
            TransactionDtoGenerator.generateTransactionDtoWithNullAmount()));
  }

  @Test
  void validateTransaction_withNonexistentEmail_returnFalse() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    assertFalse(
        validationService.validateTransaction(TransactionDtoGenerator.generateTransactionDto()));
  }

  @Test
  void validateTransaction_withNotAuthenticatedUser_returnFalse() {
    String email = "IncorrectEmail";
    BankUserAuthenticator.setAuthentication();
    when(userRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(UserGenerator.generateBankUser(email)));

    assertFalse(
        validationService.validateTransaction(TransactionDtoGenerator.generateTransactionDto()));
  }

  @Test
  void validateTransaction_withUserWhichIsNotAccountOwner_returnFalse() {
    Long incorrectAccountId = 2L;
    BankUserAuthenticator.setAuthentication();
    when(userRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(UserGenerator.generateBankUserWithBankAccount(incorrectAccountId)));

    assertFalse(
        validationService.validateTransaction(TransactionDtoGenerator.generateTransactionDto()));
  }
}
