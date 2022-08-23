package com.example.easypay.model.entity;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "balance", nullable = false)
  private BigDecimal balance;

  @Column(name = "currency", nullable = false)
  private String currency;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public BankAccount() {}

  public BankAccount(Long id) {
    this.id = id;
  }

  public BankAccount(BigDecimal balance) {
    this.balance = balance;
  }

  public BankAccount(BigDecimal balance, String currency, User user) {
    this.balance = balance;
    this.currency = currency;
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BankAccount that = (BankAccount) o;
    return Objects.equals(balance, that.balance)
        && Objects.equals(currency, that.currency)
        && Objects.equals(user, that.user);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
