package com.usercrudjdbc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        return "redirect:/oauth2/authorization/google";
    }
}