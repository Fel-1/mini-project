package com.alterra.miniproject.service;

import com.alterra.miniproject.controller.Hello;
import com.alterra.miniproject.domain.dto.HelloDTO;
import com.alterra.miniproject.util.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.alterra.miniproject.constant.AppConstant.KEY_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = HelloService.class)
class HelloServiceTest {

    @Test
    void hello_Test() {
        HelloService helloService = new HelloService();
        ResponseEntity<Object> responseEntity = helloService.hello();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}