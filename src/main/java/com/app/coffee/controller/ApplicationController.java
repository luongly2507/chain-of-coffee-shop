package com.app.coffee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }
    @GetMapping("/order")
    public String orderPage(){
        return "order";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
