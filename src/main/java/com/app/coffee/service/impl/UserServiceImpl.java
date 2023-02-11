package com.app.coffee.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.coffee.entity.User;
import com.app.coffee.exception.BadRequestException;
import com.app.coffee.exception.ConflictException;
import com.app.coffee.exception.ResourceNotFoundException;
import com.app.coffee.mapper.UserMapper;
import com.app.coffee.payload.request.CreateUserRequest;
import com.app.coffee.payload.request.UpdateUserRequest;
import com.app.coffee.payload.response.UserResponse;
import com.app.coffee.repository.UserRepository;
import com.app.coffee.service.UserService;

import jakarta.validation.Valid;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // Get Mapping - Get All
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(
                user -> userMapper.toUserResponse(user)).toList();
    }

    // Get Mapping - Get By Page
    @Override
    public Page<UserResponse> getAllUsers(String key, List<String> roles, String branchId, Pageable pageable) {
        if(roles.contains("admin")) {
            if (key != null){
                return userRepository.searchAllByAdmin(key,pageable).map(user -> userMapper.toUserResponse(user));
            } else {
                return userRepository.findAllByAdmin(pageable).map(user -> userMapper.toUserResponse(user));
            }
           
        }
        if (key != null){
            return userRepository.searchAllByManager(key, UUID.fromString(branchId),pageable).map(user -> userMapper.toUserResponse(user));
        }
        else {
            return userRepository.findAllByManager( UUID.fromString(branchId),pageable).map(user -> userMapper.toUserResponse(user));

        }
    }

    // Get Mapping - Get By Id
    @Override
    public UserResponse getUserById(UUID userId) {
        return userMapper.toUserResponse(
                userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User Not Found"))); // Check user is present
    }

    // Post Mapping - Create User
    @Override
    public UserResponse createUser(@Valid CreateUserRequest createUserRequest) {
        User user = userMapper.toUser(createUserRequest);
        // Check user is not null
        if (user == null) {
            throw new BadRequestException("Bad Request");
        }
        // Check name is unique
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new ConflictException("Email đã tồn tại.");
        }
        // Check telephone is unique
         if (userRepository.existsByTelephone(createUserRequest.getTelephone())) {
            throw new ConflictException("Số điện thoại đã được đăng ký");
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    // Put Mapping - Update User
    @Override
    public void updateUser(UUID userId, @Valid UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId)
        .orElseThrow(
        ()-> new ResourceNotFoundException("User Not Found."));
    // Check email is unique
    if (!updateUserRequest.getEmail().equals(user.getEmail())) { // compare request email and repository email
        if (userRepository.existsByEmail(updateUserRequest.getEmail())) {
            throw new ConflictException("Đã tồn tại Email này.");
        }
    } 
    // Check telephone is unique
    if (!updateUserRequest.getTelephone().equals(user.getTelephone())) { // compare request email and repository email
        if (userRepository.existsByTelephone(updateUserRequest.getTelephone())) {
            throw new ConflictException("Đã tồn tại số điện thoại này.");
        }
    }
    userMapper.updateUser(updateUserRequest, user);
    userRepository.save(user);
    }

    // Delete Mapping -
    @Override
    public void deleteUser(UUID userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new ResourceNotFoundException("User Not Found"); // Check user is present
        }
    }

    @Override
    public Page<UserResponse> getAllUsersByEmail(String search, Pageable pageable) {
        return userRepository.findByEmail(search.toLowerCase(), pageable).map(user -> userMapper.toUserResponse(user));
    }

  
}
