package com.cognizant.jwtauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    @GetMapping("/secure/ping")
    public String ping() {
        return "pong - you presented a valid JWT";
    }
}
