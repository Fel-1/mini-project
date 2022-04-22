package com.alterra.miniproject.util;

import com.alterra.miniproject.domain.dto.HelloDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ResponseUtilTest {

    @Test
    void build_Test() {
        HelloDTO data = HelloDTO.builder().content("test").build();
        String code = "SUCCESS";

        ResponseEntity<Object> responseEntity = ResponseUtil.build(data, code, HttpStatus.OK);
        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assert responseBody != null;
        HelloDTO responseData = (HelloDTO) responseBody.get("data");

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(data, responseData);
    }
}