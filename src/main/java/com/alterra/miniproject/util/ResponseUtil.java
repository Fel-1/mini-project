package com.alterra.miniproject.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    private ResponseUtil() {}

    public static ResponseEntity<Object> build(Object data, String code, HttpStatus httpStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("data", data);
        return new ResponseEntity<>(map, httpStatus);
    }

}
