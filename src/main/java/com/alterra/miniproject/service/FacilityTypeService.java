package com.alterra.miniproject.service;

import com.alterra.miniproject.constant.AppConstant;
import com.alterra.miniproject.domain.dao.FacilityType;
import com.alterra.miniproject.domain.dto.FacilityTypeDTO;
import com.alterra.miniproject.repository.FacilityTypeRepository;
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
public class FacilityTypeService {

    @Autowired
    private FacilityTypeRepository facilityTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> getAll() {
        log.info("Executing get all facility type");
        try {
            List<FacilityType> facilityTypes = facilityTypeRepository.findAll();
            List<FacilityTypeDTO> facilityTypeDTOS = new ArrayList<>();
            for (FacilityType facilityType :
                    facilityTypes) {
                facilityTypeDTOS.add(modelMapper.map(facilityType, FacilityTypeDTO.class));
            }

            log.info("Successfully retrieved all Facility Type");
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, facilityTypeDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while trying to get all facility type. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> addNew(FacilityTypeDTO request, String username) {
        log.info("Executing add new facility type");
        try {
            FacilityType facilityType = modelMapper.map(request, FacilityType.class);
            facilityType.setCreatedBy(username);
            facilityTypeRepository.save(facilityType);

            log.info("Successfully added new Facility Type");
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, modelMapper.map(facilityType, FacilityTypeDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while trying to add new facility type. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateById(Long id, FacilityTypeDTO request) {
        log.info("Executing update existing facility type");
        try {

            Optional<FacilityType> optionalFacilityType = facilityTypeRepository.findById(id);
            if(optionalFacilityType.isEmpty()) {
                log.info("Facility Type with ID [{}] not found ", id);
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            optionalFacilityType.ifPresent(facilityType -> {
                facilityType.setId(id);
                facilityType.setType(request.getType());
                facilityTypeRepository.save(facilityType);
            });

            log.info("Successfully updated Facility Type with ID : [{}]", id);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, modelMapper.map(optionalFacilityType.get(), FacilityTypeDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            log.info("An error occurred while trying to update existing facility type. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteById(Long id) {
        log.info("Executing delete existing facility type");
        try {
            Optional<FacilityType> optionalFacilityType = facilityTypeRepository.findById(id);
            if (optionalFacilityType.isEmpty()) {
                log.info("Facility Type with ID : [{}] is not found", id);
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            facilityTypeRepository.delete(optionalFacilityType.get());
            log.info("Successfully deleted Facility Type with ID : [{}]", id);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.info("An error occurred while trying to delete existing facility type. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
