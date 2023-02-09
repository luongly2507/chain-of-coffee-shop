package com.app.coffee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.coffee.payload.response.UserResponse;
import com.app.coffee.service.AuthService;

@Controller
public class ApplicationController {
  

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
