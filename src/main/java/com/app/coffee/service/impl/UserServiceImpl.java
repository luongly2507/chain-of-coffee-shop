package com.app.coffee.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.coffee.entity.RefreshToken;
import com.app.coffee.exception.ConflictException;
import com.app.coffee.exception.RefreshTokenException;
import com.app.coffee.mapper.UserMapper;
import com.app.coffee.payload.request.LoginRequest;
import com.app.coffee.payload.request.SignUpRequest;
import com.app.coffee.payload.request.TokenRefreshRequest;
import com.app.coffee.payload.response.JwtResponse;
import com.app.coffee.payload.response.TokenRefreshResponse;
import com.app.coffee.payload.response.UserResponse;
import com.app.coffee.repository.RoleRepository;
import com.app.coffee.repository.UserRepository;
import com.app.coffee.service.RefreshTokenService;
import com.app.coffee.service.UserService;
import com.app.coffee.utils.CustomUserDetails;
import com.app.coffee.utils.JwtUtils;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class UserServiceImpl implements  UserService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Override
    public UserResponse registerUser(@Valid SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ConflictException("Error: Email is already in use!");
        }
        if (userRepository.existsByTelephone(signUpRequest.getTelephone())) {
            throw new ConflictException("Error: Telephone is already in use!");
        }
        return userMapper.toUserResponse(userRepository.save(userMapper.toUser(signUpRequest)));
    }

    @Override
    public JwtResponse authenticateUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return JwtResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken.getToken())
                .id(userDetails.getId())
                .email(userDetails.getUsername())
                .roles(roles)
                .build();

    }

    @Override
    public TokenRefreshResponse refreshToken(@Valid TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromEmail(user.getEmail());
                    return new TokenRefreshResponse(token, requestRefreshToken);
                })
                .orElseThrow(() -> new RefreshTokenException(requestRefreshToken,
                        "Refresh token is not in database!"));

    }

    @Override
    public void logoutUser() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UUID userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
    }

}
