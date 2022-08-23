package com.example.easypay.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.example.easypay.exception.TransactionFailedException;
import com.example.easypay.model.dto.TransactionDto;
import com.example.easypay.model.exception.ExceptionMessage;
import com.example.easypay.service.TransactionService;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account/")
public class BankAccountController {

  private final TransactionService transactionService;

  public BankAccountController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("deposit")
  public ResponseEntity<BigDecimal> deposit(@RequestBody TransactionDto transactionDto) {
    return ResponseEntity.ok(transactionService.deposit(transactionDto));
  }

  @PostMapping("withdraw")
  public ResponseEntity<BigDecimal> withdraw(@RequestBody TransactionDto transactionDto) {
    return ResponseEntity.ok(transactionService.withdraw(transactionDto));
  }

  @ExceptionHandler(value = TransactionFailedException.class)
  public ResponseEntity<ExceptionMessage> handleTransactionFailedException(
      TransactionFailedException exception) {
    return ResponseEntity.status(BAD_REQUEST)
        .body(ExceptionMessage.getInstance(exception.getMessage()));
  }
}
