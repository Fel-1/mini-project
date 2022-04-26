package com.alterra.miniproject.controller;

import com.alterra.miniproject.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FacilityController {

    @Autowired
    FacilityService facilityService;

//    @GetMapping("facility")
//    public ResponseEntity<Object> getAllFacility() {
//        return facilityService;
//    }

}
