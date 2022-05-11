package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.dto.DoctorDetailDTO;
import com.alterra.miniproject.service.DoctorDetailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public ResponseEntity<Object> createNewDoctor(@RequestBody DoctorDetailDTO request, Principal principal) {
        if(principal!=null){
            return doctorDetailService.addNewDetail(request, principal.getName());
        }
        return doctorDetailService.addNewDetail(request, null);

    }
}
