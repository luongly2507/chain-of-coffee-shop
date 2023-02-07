package com.app.coffee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.coffee.exception.ResourceNotFoundException;
import com.app.coffee.mapper.UserMapper;
import com.app.coffee.payload.response.UserResponse;
import com.app.coffee.repository.UserRepository;
import com.app.coffee.service.AuthService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    
    public UserResponse getInformationUserFromEmail(String email) {
        return userMapper.toUserResponse(userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException())
        );
    }
}
