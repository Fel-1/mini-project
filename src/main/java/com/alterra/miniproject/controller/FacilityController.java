package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.dto.FacilityDTO;
import com.alterra.miniproject.service.FacilityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/facility")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @GetMapping("")
    @SecurityRequirements
    public ResponseEntity<Object> getAllFacility() {
        return facilityService.getAll();
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> createNewFacility(@RequestParam (value = "facilityTypeId") Long facilityTypeId,
                                                    @RequestParam (value = "locationId") Long locationId,
                                                    @RequestBody FacilityDTO request, Principal principal) {
        if(principal!=null){
            return facilityService.addNew(facilityTypeId, locationId, request, principal.getName());
        }
        return facilityService.addNew(facilityTypeId, locationId, request, null);
    }

    @PutMapping("/auth")
    public ResponseEntity<Object> updateFacility(@RequestParam (value = "id") Long id, @RequestBody FacilityDTO request) {
        return facilityService.updateById(id, request);
    }

    @DeleteMapping("/auth")
    public ResponseEntity<Object> deleteFacility(@RequestParam(value = "id") Long id) {
        return facilityService.deleteById(id);
    }

}
