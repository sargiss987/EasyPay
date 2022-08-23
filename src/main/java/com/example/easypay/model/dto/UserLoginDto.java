package com.example.easypay.model.dto;

public class UserLoginDto {

  private String email;
  private String password;

  public UserLoginDto() {}

  public UserLoginDto(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public static UserLoginDto getInstance() {
    return new UserLoginDto();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
