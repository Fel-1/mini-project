package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.dto.DoctorDTO;
import com.alterra.miniproject.domain.dto.DoctorDetailDTO;
import com.alterra.miniproject.service.DoctorDetailService;
import com.alterra.miniproject.service.DoctorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor/detail")
public class DoctorDetailController {
    @Autowired
    private DoctorDetailService doctorDetailService;

    @GetMapping("/")
    @SecurityRequirements
    public ResponseEntity<Object> getAll() {
        return doctorDetailService.getAllDoctorDetail();
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> createNewDoctor(@RequestBody DoctorDetailDTO request) {
        return doctorDetailService.addNewDetail(request);
    }
}
