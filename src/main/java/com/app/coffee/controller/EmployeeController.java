package com.app.coffee.controller;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.coffee.payload.request.CreateUserRequest;
import com.app.coffee.payload.response.UserResponse;
import com.app.coffee.service.AuthService;
import com.app.coffee.service.BranchService;
import com.app.coffee.service.UserService;

@Controller
@RequestMapping("/admin/employees")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
public class EmployeeController {

    @Autowired
    AuthService authService; 
    @Autowired
    BranchService branchService; 
    @Autowired
    UserService userService;
    
    @GetMapping
    public String getEmployeesPage (Model model, Pageable pageable ){
        UserResponse userResponse = authService.getInformationUserFromEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title","Admin | Employees Management"); 
        model.addAttribute("user", userResponse);    
        model.addAttribute("branches", branchService. getAllBranchs());    
        return "admin-employees";
    }
}