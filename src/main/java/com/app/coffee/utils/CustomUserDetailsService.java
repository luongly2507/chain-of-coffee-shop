package com.app.coffee.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.coffee.entity.Privilege;
import com.app.coffee.entity.Role;
import com.app.coffee.entity.User;
import com.app.coffee.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(userName)
        .orElseThrow(() -> new UsernameNotFoundException("Email " + userName + " not found"));
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
        getAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
    final List<GrantedAuthority> authorities = new ArrayList<>();
    final List<String> privileges = new ArrayList<>();
    final List<Privilege> collection = new ArrayList<>();
    for (final Role role : roles) {
      privileges.add(role.getName());
      collection.addAll(role.getPrivileges());
    }
    for (final Privilege item : collection) {
      privileges.add(item.getName());
    }
    for (final String privilege : privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }
    return authorities;
  }
}