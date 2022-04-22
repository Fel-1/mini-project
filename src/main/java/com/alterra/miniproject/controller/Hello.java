package com.alterra.miniproject.controller;

import com.alterra.miniproject.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Hello {

    @Autowired
    private HelloService helloService;

    @GetMapping(value = "")
    public ResponseEntity<Object> hello() {
        return helloService.hello();
    }

}
