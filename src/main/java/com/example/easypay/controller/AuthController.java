package com.example.easypay.controller;

import static com.example.easypay.constants.AuthControllerConstants.EMAIL_ALREADY_EXIST_EXCEPTION_MESSAGE_KEY;
import static com.example.easypay.constants.AuthControllerConstants.LOGIN_FORM;
import static com.example.easypay.constants.AuthControllerConstants.REGISTER_FORM;
import static com.example.easypay.constants.AuthControllerConstants.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE_KEY;
import static com.example.easypay.constants.AuthControllerConstants.USER_ACCOUNT;
import static com.example.easypay.constants.AuthControllerConstants.USER_DATA_KEY;
import static com.example.easypay.constants.AuthControllerConstants.USER_DTO_KEY;
import static com.example.easypay.constants.AuthControllerConstants.USER_PASS_INCORRECT_EXCEPTION_MESSAGE_KEY;
import static com.example.easypay.constants.AuthControllerConstants.VALIDATION_ERROR_MESSAGE_KEY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import com.example.easypay.exception.UserEmailAlreadyExistException;
import com.example.easypay.exception.UserPasswordIncorrectException;
import com.example.easypay.model.dto.UserLoginDto;
import com.example.easypay.model.dto.UserRegistrationDto;
import com.example.easypay.model.entity.User;
import com.example.easypay.model.validation.UserRegistrationValidationErrors;
import com.example.easypay.service.AuthService;
import com.example.easypay.service.UserValidationService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/v1/auth/")
public class AuthController {

  private final AuthService authService;
  private final UserValidationService validatorService;

  public AuthController(AuthService authService, UserValidationService validatorService) {
    this.authService = authService;
    this.validatorService = validatorService;
  }

  @PostMapping("register")
  public ModelAndView register(
      @ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto, Model model) {
    UserRegistrationValidationErrors validationErrors =
        validatorService.validateUserRegistrationDto(userRegistrationDto);
    if (validationErrors.hasErrors()) {
      model.addAttribute(USER_DTO_KEY, UserRegistrationDto.getInstance());
      model.addAttribute(VALIDATION_ERROR_MESSAGE_KEY, validationErrors);
      return new ModelAndView(REGISTER_FORM, BAD_REQUEST);
    }
    User user = authService.register(userRegistrationDto);
    model.addAttribute(USER_DATA_KEY, user);
    return new ModelAndView(USER_ACCOUNT, CREATED);
  }

  @PostMapping("login")
  public ModelAndView login(@ModelAttribute UserLoginDto userLoginDto, Model model) {
    authService.authenticateLoggedUser(userLoginDto);
    User user = authService.getLoggedUserByEmail(userLoginDto.getEmail());
    model.addAttribute(USER_DATA_KEY, user);
    return new ModelAndView(USER_ACCOUNT);
  }

  @ExceptionHandler(value = UserEmailAlreadyExistException.class)
  public ModelAndView userEmailAlreadyExistException(
      Model model, UserEmailAlreadyExistException exception) {
    model.addAttribute(USER_DTO_KEY, UserRegistrationDto.getInstance());
    model.addAttribute(EMAIL_ALREADY_EXIST_EXCEPTION_MESSAGE_KEY, exception.getMessage());
    return new ModelAndView(REGISTER_FORM, BAD_REQUEST);
  }

  @ExceptionHandler(value = UsernameNotFoundException.class)
  public ModelAndView userEmailAlreadyExistException(
      Model model, UsernameNotFoundException exception) {
    model.addAttribute(USER_DTO_KEY, UserLoginDto.getInstance());
    model.addAttribute(USERNAME_NOT_FOUND_EXCEPTION_MESSAGE_KEY, exception.getMessage());
    return new ModelAndView(LOGIN_FORM, BAD_REQUEST);
  }

  @ExceptionHandler(value = UserPasswordIncorrectException.class)
  public ModelAndView userPasswordIncorrectException(
      Model model, UserPasswordIncorrectException exception) {
    model.addAttribute(USER_DTO_KEY, UserLoginDto.getInstance());
    model.addAttribute(USER_PASS_INCORRECT_EXCEPTION_MESSAGE_KEY, exception.getMessage());
    return new ModelAndView(LOGIN_FORM, BAD_REQUEST);
  }
}
