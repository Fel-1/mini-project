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
    public ResponseEntity<Object> createNewDoctorDetail(@RequestParam(value = "doctorId") Long doctorId,
                                                  @RequestParam(value = "facilityId") Long facilityId,
                                                  @RequestBody DoctorDetailDTO request, Principal principal) {
        if(principal!=null){
            return doctorDetailService.addNewDetail(doctorId, facilityId, request, principal.getName());
        }
        return doctorDetailService.addNewDetail(doctorId, facilityId, request, null);

    }


    @PutMapping("/auth")
    public ResponseEntity<Object> updateDoctorDetail(@RequestParam(value = "doctorId") Long doctorId,
                                                  @RequestParam(value = "facilityId") Long facilityId,
                                                  @RequestBody DoctorDetailDTO request) {
        return doctorDetailService.updateWorkDays(doctorId, facilityId, request);

    }
    @DeleteMapping("/auth")
    public ResponseEntity<Object> deleteDoctorDetail(@RequestParam(value = "doctorId") Long doctorId,
                                                     @RequestParam(value = "facilityId") Long facilityId) {
        return doctorDetailService.deleteDoctorDetail(doctorId, facilityId);

    }
}
