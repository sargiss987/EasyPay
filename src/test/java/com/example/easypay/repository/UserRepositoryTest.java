package com.example.easypay.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import com.example.easypay.model.entity.User;
import com.example.easypay.util.UserGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class UserRepositoryTest {

  @Autowired UserRepository userRepository;

  @Test
  void findByEmail_withCorrectEmail_returnOptionalUser() {
    String email = "TestEmail";

    saveUser(UserGenerator.generateBankUser(email));

    assertTrue(userRepository.findByEmail(email).isPresent());
  }

  @Test
  void findByEmail_withIncorrectEmail_returnOptionalEmpty() {
    String email = "TestEmail";
    String incorrectEmail = "TestIncorrectEmail";

    saveUser(UserGenerator.generateBankUser(email));

    assertTrue(userRepository.findByEmail(incorrectEmail).isEmpty());
  }

  private void saveUser(User user) {
    userRepository.save(user);
  }
}
