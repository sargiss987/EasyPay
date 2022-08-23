package com.example.easypay.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.easypay.model.entity.Role;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RoleRepositoryTest {

  @Autowired RoleRepository roleRepository;

  @Test
  void checkPredefinedRolesExistence_getPredefinedRoles() {
    List<Role> userRoles = roleRepository.findAll();

    assertEquals(2, userRoles.size());
    assertTrue(userRoles.contains(Role.isBankClient()));
    assertTrue(userRoles.contains(Role.isBankEmployee()));
  }
}
