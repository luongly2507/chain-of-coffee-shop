package com.app.coffee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.coffee.payload.response.UserResponse;
import com.app.coffee.service.AuthService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
public class AdminController {

    @Autowired
    AuthService authService; 

   @GetMapping()
    public String adminPage(Model model){
        UserResponse userResponse = authService.getInformationUserFromEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title","Admin | Dashboard"); 
        model.addAttribute("user", userResponse);
        return "admin";
    }
    
   
    @GetMapping("/products")
    public String getProductsPage (Model model ){
        UserResponse userResponse = authService.getInformationUserFromEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title","Admin | Products Management"); 
        model.addAttribute("user", userResponse);
        return "admin-products";
    }
    @GetMapping("/orders")
    public String getOrdersPage (Model model ){
        UserResponse userResponse = authService.getInformationUserFromEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title","Admin | Orders Management"); 
        model.addAttribute("user", userResponse);
        return "admin-orders";
    }
    @GetMapping("/ingredients")
    public String getIngredientsPage (Model model ){
        UserResponse userResponse = authService.getInformationUserFromEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title","Admin | Ingredients Management"); 
        model.addAttribute("user", userResponse);
        return "admin-ingredients";
    }
    @GetMapping("/suppliers")
    public String getSuppliersPage (Model model ){
        UserResponse userResponse = authService.getInformationUserFromEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title","Admin | Suppliers Management"); 
        model.addAttribute("user", userResponse);
        return "admin-suppliers";
    }
    @GetMapping("/categories")
    public String getCategoriesPage (Model model ){
        UserResponse userResponse = authService.getInformationUserFromEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title","Admin | Categories Management"); 
        model.addAttribute("user", userResponse);
        return "admin-categories";
    }
    @GetMapping("/tags")
    public String getTagsPage (Model model ){
        UserResponse userResponse = authService.getInformationUserFromEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("title","Admin | Tags Management"); 
        model.addAttribute("user", userResponse);
        return "admin-tags";
    }
}
