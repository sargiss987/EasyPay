package com.example.easypay.controller;

import static com.example.easypay.constants.HomeControllerConstants.HOME_PAGE;
import static com.example.easypay.constants.HomeControllerConstants.LOGIN_FORM;
import static com.example.easypay.constants.HomeControllerConstants.REGISTER_FORM;
import static com.example.easypay.constants.HomeControllerConstants.USER_DTO_KEY;

import com.example.easypay.model.dto.UserLoginDto;
import com.example.easypay.model.dto.UserRegistrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

  @GetMapping
  public ModelAndView homePage() {
    return new ModelAndView(HOME_PAGE);
  }

  @GetMapping("/register")
  public ModelAndView register(Model model) {
    model.addAttribute(USER_DTO_KEY, UserRegistrationDto.getInstance());
    return new ModelAndView(REGISTER_FORM);
  }

  @GetMapping("/login")
  public ModelAndView login(Model model) {
    model.addAttribute(USER_DTO_KEY, UserLoginDto.getInstance());
    return new ModelAndView(LOGIN_FORM);
  }

}
