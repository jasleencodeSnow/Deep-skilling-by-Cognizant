package com.cognizant.jwtauth.controller;

import com.cognizant.jwtauth.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Demo login: in a real app, verify username/password against a user store first.
    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username) {
        return Map.of("token", jwtTokenProvider.createToken(username));
    }
}
