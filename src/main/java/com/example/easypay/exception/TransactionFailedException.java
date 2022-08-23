package com.example.easypay.exception;

public class TransactionFailedException extends RuntimeException {
  public TransactionFailedException() {}

  public TransactionFailedException(String message) {
    super(message);
  }
}
