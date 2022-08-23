package com.example.easypay.model.validation;

public class UserRegistrationValidationErrors {

  private String firstNameError;
  private String lastNameError;
  private String emailError;
  private String passwordError;
  private boolean hasErrors;

  public String getFirstNameError() {
    return firstNameError;
  }

  public void setFirstNameError(String firstNameError) {
    this.firstNameError = firstNameError;
  }

  public String getLastNameError() {
    return lastNameError;
  }

  public void setLastNameError(String lastNameError) {
    this.lastNameError = lastNameError;
  }

  public String getEmailError() {
    return emailError;
  }

  public void setEmailError(String emailError) {
    this.emailError = emailError;
  }

  public String getPasswordError() {
    return passwordError;
  }

  public void setPasswordError(String passwordError) {
    this.passwordError = passwordError;
  }

  public boolean hasErrors() {
    return hasErrors;
  }

  public void setHasErrors(boolean hasErrors) {
    this.hasErrors = hasErrors;
  }
}
