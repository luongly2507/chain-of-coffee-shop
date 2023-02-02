package com.app.coffee.data;

import java.util.Arrays;
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
        roleRepository.saveAll(Arrays.asList(
                Role.builder()
                        .name("ROLE_USER").privileges(Arrays.asList(
                                privilegeRepository.findByName("PRIVILEGE_READ_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_READ_PRODUCT"),
                                privilegeRepository.findByName("PRIVILEGE_READ_BRANCH")))
                        .build(),
                Role.builder()
                        .name("ROLE_EMPLOYEE")
                        .privileges(Arrays.asList(
                                privilegeRepository.findByName("PRIVILEGE_READ_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_READ_PRODUCT"),
                                privilegeRepository.findByName("PRIVILEGE_READ_BRANCH"),
                                privilegeRepository.findByName("PRIVILEGE_READ_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_TAG")))
                        .build(),
                Role.builder()
                        .name("ROLE_MANAGER")
                        .privileges(Arrays.asList(
                                privilegeRepository.findByName("PRIVILEGE_READ_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_READ_PRODUCT"),
                                privilegeRepository.findByName("PRIVILEGE_READ_BRANCH"),
                                privilegeRepository.findByName("PRIVILEGE_READ_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_EMPLOYEE"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_EMPLOYEE"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_EMPLOYEE"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_EMPLOYEE"),
                                privilegeRepository.findByName("PRIVILEGE_READ_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_TAG")))
                        .build(),
                Role.builder()
                        .name("ROLE_ADMIN")
                        .privileges(Arrays.asList(
                                privilegeRepository.findByName("PRIVILEGE_READ_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_CATEGORY"),
                                privilegeRepository.findByName("PRIVILEGE_READ_PRODUCT"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_PRODUCT"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_PRODUCT"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_PRODUCT"),
                                privilegeRepository.findByName("PRIVILEGE_READ_BRANCH"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_BRANCH"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_BRANCH"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_BRANCH"),
                                privilegeRepository.findByName("PRIVILEGE_READ_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_CUSTOMER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_EMPLOYEE"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_EMPLOYEE"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_EMPLOYEE"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_EMPLOYEE"),
                                privilegeRepository.findByName("PRIVILEGE_READ_MANAGER"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_MANAGER"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_MANAGER"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_MANAGER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_ORDER"),
                                privilegeRepository.findByName("PRIVILEGE_READ_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_CREATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_UPDATE_TAG"),
                                privilegeRepository.findByName("PRIVILEGE_DELETE_TAG")))
                        .build()));

    }

}
