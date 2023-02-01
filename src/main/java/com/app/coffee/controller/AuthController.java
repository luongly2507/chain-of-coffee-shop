package com.app.coffee.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.coffee.payload.request.LoginRequest;
import com.app.coffee.payload.request.SignUpRequest;
import com.app.coffee.payload.request.TokenRefreshRequest;
import com.app.coffee.payload.response.MessageResponse;
import com.app.coffee.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  UserService userService;

  @PostMapping("/sign-in")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(userService.authenticateUser(loginRequest));
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    System.out.println(signUpRequest);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(userService.registerUser(signUpRequest).getId()).toUri();
    System.out.println(location);
    return ResponseEntity.created(location).build();
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    return ResponseEntity.ok(userService.refreshToken(request));
  }

  @PostMapping("/sign-out")
  public ResponseEntity<?> logoutUser() {
    userService.logoutUser();
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }

}