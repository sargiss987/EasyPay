package com.example.easypay.model.dto;

import java.math.BigDecimal;

public class TransactionDto {

  private String email;
  private Long accountId;
  private BigDecimal amount;

  public TransactionDto() {}

  public TransactionDto(String email, Long accountId, BigDecimal amount) {
    this.email = email;
    this.accountId = accountId;
    this.amount = amount;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
