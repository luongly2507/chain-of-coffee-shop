package com.app.coffee.service;

import com.app.coffee.payload.request.LoginRequest;
import com.app.coffee.payload.request.SignUpRequest;
import com.app.coffee.payload.request.TokenRefreshRequest;
import com.app.coffee.payload.response.JwtResponse;
import com.app.coffee.payload.response.TokenRefreshResponse;
import com.app.coffee.payload.response.UserResponse;

import jakarta.validation.Valid;

public interface UserService {
    public UserResponse registerUser(@Valid SignUpRequest signUpRequest);
    public JwtResponse authenticateUser(@Valid LoginRequest loginRequest);
    public TokenRefreshResponse  refreshToken(@Valid TokenRefreshRequest request);
    public void logoutUser();
}
