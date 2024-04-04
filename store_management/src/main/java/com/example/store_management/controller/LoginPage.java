package com.example.store_management.controller;

import com.example.store_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginPage {

    private final EmployeeService employeeService;

    @Autowired
    public LoginPage(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String loginForm(){
        return "login";
    }
}
