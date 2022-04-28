package com.alterra.miniproject.util;

import com.alterra.miniproject.constant.AppConstant.ResponseCode;
import com.alterra.miniproject.domain.common.ApiResponse;
import com.alterra.miniproject.domain.common.ApiResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    private ResponseUtil() {}

    public static <T> ResponseEntity<Object> build(ResponseCode responseCode, T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(buildBody(responseCode, data) ,httpStatus);
    }

    private static <T> ApiResponse<T> buildBody(ResponseCode responseCode, T data) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .data(data)
                .status(ApiResponseStatus.builder()
                        .code(responseCode.getCode())
                        .message(responseCode.getMessage())
                        .build())
                .build();
    }

}
