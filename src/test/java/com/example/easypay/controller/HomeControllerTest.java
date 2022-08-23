package com.example.easypay.controller;

import static com.example.easypay.constants.HomeControllerConstants.HOME_PAGE;
import static com.example.easypay.constants.HomeControllerConstants.LOGIN_FORM;
import static com.example.easypay.constants.HomeControllerConstants.REGISTER_FORM;
import static com.example.easypay.constants.HomeControllerConstants.USER_DTO_KEY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void homePage_returnHomePageTemplate() throws Exception {
    mockMvc
        .perform(get("/").contentType(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(view().name(HOME_PAGE));
  }

  @Test
  void register_returnRegisterFormTemplate() throws Exception {
    mockMvc
        .perform(get("/register").contentType(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(view().name(REGISTER_FORM))
        .andExpect(model().attributeExists(USER_DTO_KEY));
  }

  @Test
  void login_returnLoginFormTemplate() throws Exception {
    mockMvc
        .perform(get("/login").contentType(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(view().name(LOGIN_FORM))
        .andExpect(model().attributeExists(USER_DTO_KEY));
  }
}
