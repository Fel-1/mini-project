package com.alterra.miniproject.util;

import com.alterra.miniproject.domain.common.ApiResponse;
import com.alterra.miniproject.domain.dto.FacilityTypeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.alterra.miniproject.constant.AppConstant.*;
import static org.junit.jupiter.api.Assertions.*;

class ResponseUtilTest {

    @Test
    void buildBody_Test() {
        FacilityTypeDTO data = FacilityTypeDTO.builder()
                .id(1L)
                .type("Test Type")
                .build();

        ResponseEntity<Object> responseEntity = ResponseUtil.build(ResponseCode.SUCCESS, data, HttpStatus.OK);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assert apiResponse != null;
        assertEquals("Test Type", ((FacilityTypeDTO) apiResponse.getData()).getType());
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

    }
}