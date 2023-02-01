package com.app.coffee.data;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.app.coffee.entity.Privilege;
import com.app.coffee.entity.Role;
import com.app.coffee.repository.PrivilegeRepository;
import com.app.coffee.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class RoleInitializeData implements CommandLineRunner {

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        privilegeRepository.saveAll(
                Arrays.asList(
                        Privilege.builder()
                                .name("PRIVILEGE_READ_CATEGORY")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_CREATE_CATEGORY")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_UPDATE_CATEGORY")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_DELETE_CATEGORY")
                                .build()));
        roleRepository.saveAll(Arrays.asList(
                Role.builder()
                        .name("ROLE_USER")
                        .build(),
                Role.builder()
                        .name("ROLE_MANAGER")
                        .privileges(Arrays.asList(
                                privilegeRepository.findByName("PRIVILEGE_READ_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_CATEGORY")))
                        .build(),
                Role.builder()
                        .name("ROLE_ADMIN")
                        .privileges(Arrays.asList(
                                privilegeRepository.findByName("PRIVILEGE_READ_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_CATEGORY")))
                        .build()));

    }

}
