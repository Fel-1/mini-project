package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.payload.UserPassword;
import com.alterra.miniproject.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @SecurityRequirements
    public ResponseEntity<?> login(@RequestBody UserPassword userPassword) {
        return authService.register(userPassword);
    }

    @PostMapping("/login")
    @SecurityRequirements
    public ResponseEntity<?> generateToken(@RequestBody UserPassword req) {
        return authService.generateToken(req);
    }
}
