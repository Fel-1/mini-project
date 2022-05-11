package com.alterra.miniproject.service;

import com.alterra.miniproject.constant.AppConstant;
import com.alterra.miniproject.domain.dao.Location;
import com.alterra.miniproject.domain.dto.LocationDTO;
import com.alterra.miniproject.repository.LocationRepository;
import com.alterra.miniproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> getAll() {
        log.info("Executing get all location");
        try {
            List<Location> locations = locationRepository.findAll();
            List<LocationDTO> locationDTOS = new ArrayList<>();
            for (Location location :
                    locations) {
                locationDTOS.add(modelMapper.map(location, LocationDTO.class));
            }

            log.info("Successfully retrieved all Location");
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, locationDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while trying to get all location. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> addNew(LocationDTO request, String username) {
        log.info("Executing add new location");
        try {
            Location location = modelMapper.map(request, Location.class);
            location.setCreatedBy(username);
            locationRepository.save(location);

            log.info("Successfully added new Location");
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, modelMapper.map(location, LocationDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while trying to add new location. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateById(Long id, LocationDTO request) {
        log.info("Executing update existing location");
        try {

            Optional<Location> optionalLocation = locationRepository.findById(id);
            if(optionalLocation.isEmpty()) {
                log.info("Location with ID [{}] not found ", id);
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            optionalLocation.ifPresent(location -> {
                location.setId(id);
                location.setKelurahan(request.getKelurahan());
                location.setKecamatan(request.getKecamatan());
                location.setKota(request.getKota());
                location.setProvinsi(request.getProvinsi());
                locationRepository.save(location);
            });

            log.info("Successfully updated Location with ID : [{}]", id);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, modelMapper.map(optionalLocation.get(), LocationDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            log.info("An error occurred while trying to update existing location. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteById(Long id) {
        log.info("Executing delete existing location");
        try {
            Optional<Location> optionalLocation = locationRepository.findById(id);
            if (optionalLocation.isEmpty()) {
                log.info("Location with ID : [{}] is not found", id);
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            locationRepository.delete(optionalLocation.get());
            log.info("Successfully deleted Location with ID : [{}]", id);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.info("An error occurred while trying to delete existing location. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
