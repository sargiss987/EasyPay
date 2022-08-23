package com.example.easypay.service.impl;

import static com.example.easypay.constants.ExceptionHandlingConstants.INSUFFICIENT_BALANCE;

import com.example.easypay.exception.TransactionFailedException;
import com.example.easypay.model.dto.TransactionDto;
import com.example.easypay.repository.BankAccountRepository;
import com.example.easypay.service.TransactionService;
import com.example.easypay.service.TransactionValidationService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

  private final BankAccountRepository bankAccountRepository;
  private final TransactionValidationService transactionValidationService;

  public TransactionServiceImpl(
      BankAccountRepository bankAccountRepository,
      TransactionValidationService transactionValidationService) {
    this.bankAccountRepository = bankAccountRepository;
    this.transactionValidationService = transactionValidationService;
  }

  @Override
  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public BigDecimal deposit(TransactionDto transactionDto) {
    if (!transactionValidationService.validateTransaction(transactionDto)) {
      throw new TransactionFailedException();
    }
    return bankAccountRepository
        .findById(transactionDto.getAccountId())
        .map(
            bankAccount -> {
              BigDecimal updatedBalance = bankAccount.getBalance().add(transactionDto.getAmount());
              bankAccount.setBalance(updatedBalance);
              bankAccountRepository.save(bankAccount);
              return updatedBalance;
            })
        .orElseThrow(TransactionFailedException::new);
  }

  @Override
  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public BigDecimal withdraw(TransactionDto transactionDto) {
      if (!transactionValidationService.validateTransaction(transactionDto)) {
          throw new TransactionFailedException();
      }
      return bankAccountRepository
              .findById(transactionDto.getAccountId())
              .map(
                      bankAccount -> {
                          if (bankAccount.getBalance().compareTo(transactionDto.getAmount()) >= 0){
                              BigDecimal updatedBalance = bankAccount.getBalance().subtract(transactionDto.getAmount());
                              bankAccount.setBalance(updatedBalance);
                              bankAccountRepository.save(bankAccount);
                              return updatedBalance;
                          }
                          throw new TransactionFailedException(INSUFFICIENT_BALANCE);
                      })
              .orElseThrow(TransactionFailedException::new);
  }
}
