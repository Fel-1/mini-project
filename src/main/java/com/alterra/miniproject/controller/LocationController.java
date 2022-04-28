package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.dto.FacilityTypeDTO;
import com.alterra.miniproject.domain.dto.LocationDTO;
import com.alterra.miniproject.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("")
    public ResponseEntity<Object> getAllLocation() {
        return locationService.getAll();
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> createNewLocation(@RequestBody LocationDTO request) {
        return locationService.addNew(request);
    }

    @PutMapping("/auth")
    public ResponseEntity<Object> updateLocation(@RequestParam (value = "id") Long id, @RequestBody LocationDTO request) {
        return locationService.updateById(id, request);
    }

    @DeleteMapping("/auth")
    public ResponseEntity<Object> deleteLocation(@RequestParam(value = "id") Long id) {
        return locationService.deleteById(id);
    }

}
