package com.example.easypay.service.impl;

import com.example.easypay.model.dto.TransactionDto;
import com.example.easypay.model.entity.User;
import com.example.easypay.repository.UserRepository;
import com.example.easypay.service.TransactionValidationService;
import java.math.BigDecimal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TransactionValidationServiceImpl implements TransactionValidationService {

  private final UserRepository userRepository;

  public TransactionValidationServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean validateTransaction(TransactionDto transactionDto) {
    if (isAmountEmpty(transactionDto.getAmount())) {
      return false;
    }
    return userRepository
        .findByEmail(transactionDto.getEmail())
        .map(user -> isUserAccountOwner(user, transactionDto.getAccountId()))
        .orElse(false);
  }

  private boolean isAmountEmpty(BigDecimal amount) {
    return amount == null || amount.doubleValue() <= 0;
  }

  private boolean isUserAccountOwner(User user, Long accountId) {
    if (!isUserAuthenticated(user.getEmail())) {
      return false;
    }
    return user.getAccounts().stream()
        .anyMatch(bankAccount -> (bankAccount.getId().equals(accountId)));
  }

  private boolean isUserAuthenticated(String email) {
    return email.equals(getUserEmailFromSecurityContext());
  }

  private String getUserEmailFromSecurityContext() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
