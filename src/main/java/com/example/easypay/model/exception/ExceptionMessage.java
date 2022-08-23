package com.example.easypay.model.exception;

public class ExceptionMessage {

  private final String errorMessage;

  public ExceptionMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public static ExceptionMessage getInstance(String errorMessage) {
    return new ExceptionMessage(errorMessage);
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
