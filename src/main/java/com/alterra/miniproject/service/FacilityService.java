package com.alterra.miniproject.service;

import com.alterra.miniproject.domain.dao.Facility;
import com.alterra.miniproject.domain.dao.FacilityType;
import com.alterra.miniproject.domain.dao.Location;
import com.alterra.miniproject.domain.dto.FacilityDTO;
import com.alterra.miniproject.repository.FacilityRepository;
import com.alterra.miniproject.repository.FacilityTypeRepository;
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

import static com.alterra.miniproject.constant.AppConstant.*;

@Service
@Slf4j
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private FacilityTypeRepository facilityTypeRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> getAll() {
        log.info("Executing get all facility");
        try {
            //Get all facility entity as list and map each object to DTO, then return the list by response util
            List<Facility> facilityList = facilityRepository.findAll();
            List<FacilityDTO> facilityDTOList = new ArrayList<>();
            for (Facility facility :
                    facilityList) {
                facilityDTOList.add(modelMapper.map(facility, FacilityDTO.class));
            }

            log.info("Successfully retrieved all Facility");
            return ResponseUtil.build(ResponseCode.SUCCESS, facilityDTOList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while trying to get all facility. Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> addNew(FacilityDTO request, String username) {
        log.info("Executing add new facility");
        try {
            //Checking facility type and location exist or not on database
            Optional<FacilityType> facilityType = facilityTypeRepository.findById(request.getFacilityType().getId());
            if(facilityType.isEmpty()) {
                log.info("Facility Type with ID [{}] not found ", request.getFacilityType().getId());
                return ResponseUtil.build(ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Optional<Location> location = locationRepository.findById(request.getLocation().getId());
            if(location.isEmpty()) {
                log.info("Location with ID : [{}] is not found", request.getLocation().getId());
                return ResponseUtil.build(ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Facility facility = modelMapper.map(request, Facility.class);
            facility.setFacilityType(facilityType.get());
            facility.setLocation(location.get());
            facility.setCreatedBy(username);
            facilityRepository.save(facility);

            log.info("Successfully added new Facility");
            return ResponseUtil.build(ResponseCode.SUCCESS, modelMapper.map(facility, FacilityDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while trying to add new facility. Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateById(Long id, FacilityDTO request) {
        log.info("Executing update existing facility");
        try {

            Optional<Facility> optionalFacility = facilityRepository.findById(id);
            if(optionalFacility.isEmpty()) {
                log.info("Facility with ID : [{}] is not found", id);
                return ResponseUtil.build(ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Optional<FacilityType> facilityType = facilityTypeRepository.findById(request.getFacilityType().getId());
            if(facilityType.isEmpty()) {
                log.info("Facility Type with ID [{}] not found ", request.getFacilityType().getId());
                return ResponseUtil.build(ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Optional<Location> location = locationRepository.findById(request.getLocation().getId());
            if(location.isEmpty()) {
                log.info("Location with ID : [{}] is not found", request.getLocation().getId());
                return ResponseUtil.build(ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            optionalFacility.ifPresent(facility -> {
                facility.setId(id);
                facility.setName(request.getName());
                facility.setMapUrl(request.getMapUrl());
                facility.setWebsiteUrl(request.getWebsiteUrl());
                facility.setAddress(request.getAddress());
                facility.setLocation(location.get());
                facility.setFacilityType(facilityType.get());
                facilityRepository.save(facility);
            });

            log.info("Successfully updated Facility with ID : [{}]", id);
            return ResponseUtil.build(ResponseCode.SUCCESS, modelMapper.map(optionalFacility.get(), FacilityDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            log.info("An error occurred while trying to update existing facility. Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteById(Long id) {
        log.info("Executing delete existing facility");
        try {
            Optional<Facility> optionalFacility = facilityRepository.findById(id);
            if (optionalFacility.isEmpty()) {
                log.info("Facility with ID : [{}] is not found", id);
                return ResponseUtil.build(ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            facilityRepository.delete(optionalFacility.get());
            log.info("Successfully deleted Facility with ID : [{}]", id);
            return ResponseUtil.build(ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.info("An error occurred while trying to delete existing facility. Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
