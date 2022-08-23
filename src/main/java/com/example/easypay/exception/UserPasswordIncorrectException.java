package com.example.easypay.exception;

public class UserPasswordIncorrectException extends RuntimeException {

  public UserPasswordIncorrectException(String message) {
    super(message);
  }
}
