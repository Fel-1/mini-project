package com.alterra.miniproject.service;

import com.alterra.miniproject.constant.AppConstant;
import com.alterra.miniproject.domain.dao.Doctor;
import com.alterra.miniproject.domain.dao.DoctorDetail;
import com.alterra.miniproject.domain.dao.Facility;
import com.alterra.miniproject.domain.dto.DoctorDetailDTO;
import com.alterra.miniproject.repository.DoctorDetailRepository;
import com.alterra.miniproject.repository.DoctorRepository;
import com.alterra.miniproject.repository.FacilityRepository;
import com.alterra.miniproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DoctorDetailService {
    @Autowired
    private DoctorDetailRepository doctorDetailRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> getAllDoctorDetail() {
        log.info("Executing get all doctor detail");
        try {
            List<DoctorDetail> doctorDetails = doctorDetailRepository.findAll();
            List<DoctorDetailDTO> doctorDetailDTOS = new ArrayList<>();
            for (DoctorDetail doctorDetail :
                    doctorDetails) {
                doctorDetailDTOS.add(modelMapper.map(doctorDetail, DoctorDetailDTO.class));
            }

            log.info("Successfully retrieved all Doctor");
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, doctorDetailDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while trying to get all doctor. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Object> addNewDetail(DoctorDetailDTO request) {
        log.info("Executing add new doctor detail");
        try {
            Optional<Facility> facility = facilityRepository.findById(request.getFacility().getId());
            if(facility.isEmpty()) {
                log.info("Facility Type with ID [{}] not found ", request.getFacility().getId());
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Optional<Doctor> doctor = doctorRepository.findById(request.getDoctor().getId());
            if(doctor.isEmpty()) {
                log.info("Location with ID : [{}] is not found", request.getDoctor().getId());
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            DoctorDetail doctorDetail = modelMapper.map(request, DoctorDetail.class);
            doctorDetail.setDoctor(doctor.get());
            doctorDetail.setFacility(facility.get());
            doctorDetailRepository.save(doctorDetail);

            log.info("Successfully added new Doctor detail");
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, modelMapper.map(doctorDetail, DoctorDetailDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while trying to add new doctor detail. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
