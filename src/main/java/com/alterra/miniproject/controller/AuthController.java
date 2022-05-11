package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.payload.UserPassword;
import com.alterra.miniproject.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @SecurityRequirements
    public ResponseEntity<Object> login(@RequestBody UserPassword userPassword, Principal principal) {
        if(principal!= null){
            return authService.register(userPassword, principal.getName());
        }
        return authService.register(userPassword, null);
    }

    @PostMapping("/login")
    @SecurityRequirements
    public ResponseEntity<Object> generateToken(@RequestBody UserPassword req) {
        return authService.generateToken(req);
    }
}
