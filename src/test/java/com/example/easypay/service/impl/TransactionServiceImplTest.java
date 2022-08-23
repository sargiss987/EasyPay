package com.example.easypay.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.example.easypay.exception.TransactionFailedException;
import com.example.easypay.repository.BankAccountRepository;
import com.example.easypay.service.TransactionService;
import com.example.easypay.service.TransactionValidationService;
import com.example.easypay.util.BankAccountGenerator;
import com.example.easypay.util.TransactionDtoGenerator;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

  @Mock BankAccountRepository bankAccountRepository;

  @Mock TransactionValidationService transactionValidationService;

  private TransactionService transactionService;

  @BeforeEach
  void setUp() {
    transactionService =
        new TransactionServiceImpl(bankAccountRepository, transactionValidationService);
  }

  @Test
  void deposit_withViolatedValidationRules_throwTransactionFailedException() {
    when(transactionValidationService.validateTransaction(any()))
        .thenThrow(TransactionFailedException.class);

    assertThatThrownBy(() -> transactionService.deposit(any()))
        .isInstanceOf(TransactionFailedException.class);
  }

  @Test
  void deposit_withIncorrectAccountId_throwTransactionFailedException() {
    when(transactionValidationService.validateTransaction(any())).thenReturn(true);
    when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThatThrownBy(
            () -> transactionService.deposit(TransactionDtoGenerator.generateTransactionDto()))
        .isInstanceOf(TransactionFailedException.class);
  }

  @Test
  void deposit_withCorrectCredentials_returnUpdatedBalance() {
    BigDecimal expectedBalance = BigDecimal.valueOf(223.000);

    when(transactionValidationService.validateTransaction(any())).thenReturn(true);
    when(bankAccountRepository.findById(anyLong()))
        .thenReturn(Optional.of(BankAccountGenerator.generateBankAccountWithBalance()));

    assertEquals(transactionService.deposit(TransactionDtoGenerator.generateTransactionDto()),expectedBalance);
  }

  @Test
  void withdraw_withViolatedValidationRules_throwTransactionFailedException() {
    when(transactionValidationService.validateTransaction(any()))
        .thenThrow(TransactionFailedException.class);

    assertThatThrownBy(() -> transactionService.withdraw(any()))
        .isInstanceOf(TransactionFailedException.class);
  }

  @Test
  void withdraw_withIncorrectAccountId_throwTransactionFailedException() {
    when(transactionValidationService.validateTransaction(any())).thenReturn(true);
    when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThatThrownBy(
            () -> transactionService.withdraw(TransactionDtoGenerator.generateTransactionDto()))
        .isInstanceOf(TransactionFailedException.class);
  }

  @Test
  void withdraw_withCorrectCredentials_returnUpdatedBalance() {
    BigDecimal expectedBalance = BigDecimal.valueOf(23.000);

    when(transactionValidationService.validateTransaction(any())).thenReturn(true);
    when(bankAccountRepository.findById(anyLong()))
            .thenReturn(Optional.of(BankAccountGenerator.generateBankAccountWithBalance()));

    assertEquals(transactionService.withdraw(TransactionDtoGenerator.generateTransactionDto()),expectedBalance);
  }

  @Test
  void withdraw_withInsufficientBalance_throwTransactionFailedException() {
    BigDecimal insufficientBalance = BigDecimal.valueOf(50);

    when(transactionValidationService.validateTransaction(any())).thenReturn(true);
    when(bankAccountRepository.findById(anyLong()))
            .thenReturn(Optional.of(BankAccountGenerator.generateBankAccountWithBalance(insufficientBalance)));

    assertThatThrownBy(
            () -> transactionService.withdraw(TransactionDtoGenerator.generateTransactionDto()))
            .isInstanceOf(TransactionFailedException.class);
  }
}
