package com.app.coffee.mapper.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.coffee.entity.Role;
import com.app.coffee.entity.User;
import com.app.coffee.exception.ResourceNotFoundException;
import com.app.coffee.mapper.BranchMapper;
import com.app.coffee.mapper.UserMapper;
import com.app.coffee.payload.request.CreateUserRequest;
import com.app.coffee.payload.request.UpdateUserRequest;
import com.app.coffee.payload.response.UserResponse;
import com.app.coffee.repository.BranchRepository;
import com.app.coffee.repository.RoleRepository;

@Component
public class UserMapperImpl implements UserMapper{
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    BranchMapper branchMapper;
    @Override
    public UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse
            .builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .gender(user.getGender())
            .telephone(user.getTelephone())
            .birthday(user.getBirthday())
            .address(user.getAddress())
            .roles(user.getRoles().stream().map(r -> r.getName()).toList())
            .createdAt(user.getCreatedAt())
            .createdBy(user.getCreatedBy())
            .branches(branchMapper.toBranchResponse(user.getBranch()))
            .lastModifiedAt(user.getLastModifiedAt())
            .lastModifiedBy(user.getLastModifiedBy())
            .build();
    }

    @Override
    public User toUser(CreateUserRequest signUpRequest) {
        if (signUpRequest == null) {
            return null;
        }
        User user = User.builder()
                    .name(signUpRequest.getName())
                    .gender(signUpRequest.getGender())
                    .email(signUpRequest.getEmail())
                    .birthday(signUpRequest.getBirthday())
                    .telephone(signUpRequest.getTelephone())
                    .address(signUpRequest.getAddress())
                    .branch(branchRepository.findById(signUpRequest.getBranch()).orElseThrow(()->new ResourceNotFoundException()))
                    .password(encoder.encode(signUpRequest.getPassword()))
                    .build();
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "manager":
                        Role modRole = roleRepository.findByName("ROLE_MANAGER")
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName("ROLE_EMPLOYEE")
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        return user;
    }

    @Override
    public void updateUser(UpdateUserRequest updateUser, User user) {
        if(updateUser.getPassword().length() >= 6){
            user.setPassword(encoder.encode(updateUser.getPassword()));
        }
        user.setName(updateUser.getName());
        user.setTelephone(updateUser.getTelephone());
        user.setEmail(updateUser.getEmail());
        user.setGender(updateUser.getGender());
        user.setBirthday(updateUser.getBirthday());
        user.setAddress(updateUser.getAddress());
        Set<String> strRoles = updateUser.getRole();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "manager":
                    Role modRole = roleRepository.findByName("ROLE_MANAGER")
                            .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                    roles.add(modRole);
                    break;
                default:
                    Role userRole = roleRepository.findByName("ROLE_EMPLOYEE")
                            .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        user.setBranch(branchRepository.findById(updateUser.getBranch()).orElseThrow(()->new ResourceNotFoundException()));
    }
    
}
