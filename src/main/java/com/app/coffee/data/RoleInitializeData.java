package com.app.coffee.data;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.coffee.entity.Privilege;
import com.app.coffee.entity.Role;
import com.app.coffee.entity.User;
import com.app.coffee.repository.PrivilegeRepository;
import com.app.coffee.repository.RoleRepository;
import com.app.coffee.repository.UserRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class RoleInitializeData implements CommandLineRunner {

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        savePrivilege();
        saveRole();
        saveUser();

    }

    @Order(1)
    private void savePrivilege() {
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
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_READ_PRODUCT")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_CREATE_PRODUCT")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_UPDATE_PRODUCT")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_DELETE_PRODUCT")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_READ_EMPLOYEEE")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_CREATE_EMPLOYEEE")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_UPDATE_EMPLOYEEE")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_DELETE_EMPLOYEEE")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_READ_MANAGER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_CREATE_MANAGER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_UPDATE_MANAGER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_DELETE_MANAGER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_READ_CUSTOMER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_CREATE_CUSTOMER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_UPDATE_CUSTOMER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_DELETE_CUSTOMER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_READ_ORDER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_CREATE_ORDER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_UPDATE_ORDER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_DELETE_ORDER")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_READ_BRANCH")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_CREATE_BRANCH")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_UPDATE_BRANCH")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_DELETE_BRANCH")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_READ_TAG")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_CREATE_TAG")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_UPDATE_TAG")
                                .build(),
                        Privilege.builder()
                                .name("PRIVILEGE_DELETE_TAG")
                                .build()

                ));
    }

    @Order(2)
    private void saveRole() {
        roleRepository.saveAll(Arrays.asList(
                Role.builder()
                        .name("ROLE_USER").privileges(Arrays.asList(
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_CATEGORY"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_PRODUCT"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_BRANCH")))
                        .build(),
                Role.builder()
                        .name("ROLE_EMPLOYEE")
                        .privileges(Arrays.asList(
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_CATEGORY"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_PRODUCT"),
                                privilegeRepository.findByName("PRIVILEGE_READ_BRANCH"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_CUSTOMER"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_CREATE_CUSTOMER"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_UPDATE_CUSTOMER"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_DELETE_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_ORDER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_CREATE_ORDER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_UPDATE_ORDER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_DELETE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_TAG")))
                        .build(),
                Role.builder()
                        .name("ROLE_MANAGER")
                        .privileges(Arrays.asList(
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_CATEGORY"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_PRODUCT"),
                                privilegeRepository.findByName("PRIVILEGE_READ_BRANCH"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_CUSTOMER"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_CREATE_CUSTOMER"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_UPDATE_CUSTOMER"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_DELETE_CUSTOMER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_EMPLOYEE"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_CREATE_EMPLOYEE"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_UPDATE_EMPLOYEE"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_DELETE_EMPLOYEE"),
                                privilegeRepository.findByName("PRIVILEGE_READ_ORDER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_CREATE_ORDER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_UPDATE_ORDER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_DELETE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_TAG")))
                        .build(),
                Role.builder()
                        .name("ROLE_ADMIN")
                        .privileges(Arrays.asList(
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_CATEGORY"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_CREATE_CATEGORY"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_UPDATE_CATEGORY"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_DELETE_CATEGORY"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_PRODUCT"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_CREATE_PRODUCT"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_UPDATE_PRODUCT"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_DELETE_PRODUCT"),
                                privilegeRepository.findByName("PRIVILEGE_READ_BRANCH"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_CREATE_BRANCH"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_UPDATE_BRANCH"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_DELETE_BRANCH"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_CUSTOMER"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_CREATE_CUSTOMER"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_UPDATE_CUSTOMER"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_DELETE_CUSTOMER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_EMPLOYEE"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_CREATE_EMPLOYEE"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_UPDATE_EMPLOYEE"),
                                privilegeRepository.findByName(
                                        "PRIVILEGE_DELETE_EMPLOYEE"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_READ_MANAGER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_CREATE_MANAGER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_UPDATE_MANAGER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_DELETE_MANAGER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_ORDER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_CREATE_ORDER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_UPDATE_ORDER"),
                                privilegeRepository
                                        .findByName("PRIVILEGE_DELETE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_TAG")))
                        .build()));
    }

    @Order(3)
    private void saveUser() {
        userRepository.saveAll(
                Arrays.asList(
                        User.builder()
                                .email("admin@gmail.com")
                                .password(passwordEncoder.encode("admin"))
                                .address("Unknown")
                                .name("Admin").birthday(LocalDate.parse("2000-01-01"))
                                .gender("Unknown")
                                .telephone("001")
                                .roles(Arrays.asList(roleRepository
                                        .findByName("ROLE_ADMIN").get()))
                                .build(),
                        User.builder()
                                .email("manager@gmail.com")
                                .password(passwordEncoder.encode("manager"))
                                .address("Unknown")
                                .name("Manager")
                                .gender("Unknown")
                                .birthday(LocalDate.parse("2000-01-01"))
                                .telephone("002")
                                .roles(Arrays.asList(roleRepository
                                        .findByName("ROLE_MANAGER").get()))
                                .build(),
                        User.builder()
                                .email("employeee@gmail.com")
                                .password(passwordEncoder.encode("employee"))
                                .address("Unknown")
                                .name("Employee")
                                .telephone("003")
                                .gender("Unknown")
                                .birthday(LocalDate.parse("2000-01-01"))
                                .roles(Arrays.asList(roleRepository
                                        .findByName("ROLE_EMPLOYEE").get()))
                                .build()));
    }
}
