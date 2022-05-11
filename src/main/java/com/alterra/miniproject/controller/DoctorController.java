package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.dto.DoctorDTO;
import com.alterra.miniproject.service.DoctorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

//    @GetMapping("")
//    @SecurityRequirements
//    public ResponseEntity<Object> getAllDoctor() {
//    }
    @GetMapping("/")
    @SecurityRequirements
    public ResponseEntity<Object> getDoctorById(@RequestParam (value = "id", required = false) Long id) {
        if(id == null){
            return doctorService.getAll();
        }else{
            return doctorService.getById(id);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> createNewDoctor(@RequestBody DoctorDTO request) {
        return doctorService.addNew(request);
    }

    @PutMapping("/auth")
    public ResponseEntity<Object> updateDoctor(@RequestParam (value = "id") Long id, @RequestBody DoctorDTO request) {
        return doctorService.updateById(id, request);
    }

    @DeleteMapping("/auth")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Object> deleteDoctor(@RequestParam(value = "id") Long id) {
        return doctorService.deleteById(id);
    }

}
