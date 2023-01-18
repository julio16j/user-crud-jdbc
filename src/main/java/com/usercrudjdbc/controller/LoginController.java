package com.usercrudjdbc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class LoginController {
	
	@Operation(hidden = true)
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        return "redirect:/oauth2/authorization/google";
    }
}