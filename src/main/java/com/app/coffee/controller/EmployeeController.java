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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.coffee.payload.response.UserResponse;
import com.app.coffee.service.AuthService;
import com.app.coffee.service.UserService;

@Controller
@RequestMapping("/admin/employees")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
public class EmployeeController {

    @Autowired
    AuthService authService; 

    @Autowired
    UserService userService;
    
    @GetMapping
    public String getEmployeesPage (Model model, Pageable pageable ){
        UserResponse userResponse = authService.getInformationUserFromEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title","Admin | Employees Management"); 
        model.addAttribute("user", userResponse);    
        return  findPaginated(1,"name","asc",model,  userResponse.getRoles(), userResponse.getBranches().getId());
    }
    @GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model,
            List<String> roles, UUID branch) {
		int pageSize = 1;	
		Page<UserResponse> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir, roles,branch);
		List<UserResponse> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("hasPrevious", page.hasPrevious());
        model.addAttribute("hasNext", page.hasNext());
        model.addAttribute("isFirst", page.isFirst());
        model.addAttribute("isLast", page.isLast());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("listUsers", listEmployees);
        return "admin-employees";
	}
    @GetMapping("/add")
    public String getAddEmployeePage (Model model, Pageable pageable ){
        UserResponse userResponse = authService.getInformationUserFromEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title","Admin | Add Employees"); 
        model.addAttribute("user", userResponse);    
        return  "admin-add-employee";
    }
}