package com.example.easypay.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.easypay.model.entity.BankAccount;
import com.example.easypay.model.entity.User;
import com.example.easypay.repository.BankAccountRepository;
import com.example.easypay.repository.UserRepository;
import com.example.easypay.util.BankAccountDtoGenerator;
import com.example.easypay.util.BankAccountGenerator;
import com.example.easypay.util.BankUserAuthenticator;
import com.example.easypay.util.UserGenerator;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BankServiceImplTest {

  @Mock UserRepository userRepository;

  @Mock BankAccountRepository bankAccountRepository;

  private BankServiceImpl bankService;

  @BeforeEach
  void setUp() {
    bankService = new BankServiceImpl(userRepository, bankAccountRepository);
  }

  @Test
  void createAccount_withIncorrectCredentials_throwIllegalArgumentException() {
    BankUserAuthenticator.setAuthentication();

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    assertThatThrownBy(
            () -> bankService.createAccount(BankAccountDtoGenerator.generateBankAccountDto()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void createAccount_withCorrectCredentials_returnUserWithAddedBankAccount() {
    BankUserAuthenticator.setAuthentication();

    when(userRepository.findByEmail(anyString()))
        .thenReturn(Optional.of(UserGenerator.generateBankUser()));
    when(userRepository.save(any())).thenReturn(UserGenerator.generateBankUserWithBankAccount());

    User user = bankService.createAccount(BankAccountDtoGenerator.generateBankAccountDto());

    assertEquals(user.getAccounts().size(), 1);
  }

  @Test
  void getAccountById_withCorrectId_returnBankAccount() {
    long id = 1L;
    BankAccount bankAccount =
        BankAccountGenerator.generateBankAccountWithUser(UserGenerator.generateBankUser());

    BankUserAuthenticator.setAuthentication();
    when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(bankAccount));

    assertEquals(bankService.getAccountById(id), bankAccount);
  }

  @Test
  void getAccountById_withIncorrectId_throwIllegalArgumentException() {
    long id = 1L;
    when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> bankService.getAccountById(id))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getAccountById_notBelongsToCurrentUser_throwIllegalArgumentException() {
    long id = 1L;
    String accountUserEmail = "accountUserEmail";
    BankAccount bankAccount =
        BankAccountGenerator.generateBankAccountWithUser(
            UserGenerator.generateBankUser(accountUserEmail));

    BankUserAuthenticator.setAuthentication();
    when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(bankAccount));

    assertThatThrownBy(() -> bankService.getAccountById(id))
        .isInstanceOf(IllegalArgumentException.class);
  }

}
