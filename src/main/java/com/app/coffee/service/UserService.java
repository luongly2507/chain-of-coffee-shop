package com.app.coffee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.coffee.payload.request.CreateUserRequest;
import com.app.coffee.payload.request.UpdateUserRequest;
import com.app.coffee.payload.response.UserResponse;

import jakarta.validation.Valid;

public interface UserService {
    public List<UserResponse> getAllUsers();
    public Page<UserResponse> getAllUsers(String key, List<String> roles,String branch, Pageable pageable);
    public UserResponse getUserById(UUID userId);
    public void deleteUser(UUID userId);
    public UserResponse createUser(@Valid CreateUserRequest createUserRequest);
    public void updateUser(UUID userId, @Valid UpdateUserRequest updateUserRequest);
    public Page<UserResponse> getAllUsersByEmail(String search, Pageable pageable);
    public long getCountUser();
}
