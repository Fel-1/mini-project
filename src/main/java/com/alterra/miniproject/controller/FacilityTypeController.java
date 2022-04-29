package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.dto.FacilityDTO;
import com.alterra.miniproject.domain.dto.FacilityTypeDTO;
import com.alterra.miniproject.service.FacilityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/type")
public class FacilityTypeController {

    @Autowired
    private FacilityTypeService facilityTypeService;

    @GetMapping("")
    public ResponseEntity<Object> getAllFacilityType() {
        return facilityTypeService.getAll();
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> createNewFacilityType(@RequestBody FacilityTypeDTO request) {
        return facilityTypeService.addNew(request);
    }

    @PutMapping("/auth")
    public ResponseEntity<Object> updateFacilityType(@RequestParam (value = "id") Long id, @RequestBody FacilityTypeDTO request) {
        return facilityTypeService.updateById(id, request);
    }

    @DeleteMapping("/auth")
    public ResponseEntity<Object> deleteFacilityType(@RequestParam(value = "id") Long id) {
        return facilityTypeService.deleteById(id);
    }
}
