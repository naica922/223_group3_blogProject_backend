package com.example.demo.domain.user;

import com.example.demo.core.generic.AbstractService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService, AbstractService<User> {
  User register(User user);

  User registerUser(User user);

  User getByUsername(String email);

  // NEW: Fetch users without a group
  List<User> getUsersWithoutGroup();
}
