package com.app.coffee.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.coffee.entity.User;
import com.app.coffee.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    if (user == null) {
        throw new UsernameNotFoundException(username);
    }
    return CustomUserDetails.build(user);
  }

}