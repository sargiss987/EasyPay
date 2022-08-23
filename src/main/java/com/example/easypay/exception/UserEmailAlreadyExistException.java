package com.example.easypay.exception;

public class UserEmailAlreadyExistException extends RuntimeException {
  public UserEmailAlreadyExistException(String message) {
    super(message);
  }
}
