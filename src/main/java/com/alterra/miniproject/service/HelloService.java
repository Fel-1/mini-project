package com.alterra.miniproject.service;

import com.alterra.miniproject.domain.dto.HelloDTO;
import com.alterra.miniproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.alterra.miniproject.constant.AppConstant.KEY_SUCCESS;

@Slf4j
@Service
public class HelloService {

    public ResponseEntity<Object> hello() {
        HelloDTO helloDTO = HelloDTO.builder()
                .content("Hello World")
                .build();

        return ResponseUtil.build(helloDTO, KEY_SUCCESS, HttpStatus.OK);
    }
}
