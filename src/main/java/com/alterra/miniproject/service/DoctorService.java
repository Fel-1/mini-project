package com.alterra.miniproject.service;

import com.alterra.miniproject.constant.AppConstant;
import com.alterra.miniproject.domain.dao.Doctor;
import com.alterra.miniproject.domain.dto.DoctorDTO;
import com.alterra.miniproject.repository.DoctorRepository;
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
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> getAll() {
        log.info("Executing get all doctor");
        try {
            List<Doctor> doctors = doctorRepository.findAll();
            List<DoctorDTO> doctorDTOS = new ArrayList<>();
            for (Doctor doctor :
                    doctors) {
                doctorDTOS.add(modelMapper.map(doctor, DoctorDTO.class));
            }

            log.info("Successfully retrieved all Doctor");
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, doctorDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while trying to get all doctor. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> addNew(DoctorDTO request) {
        log.info("Executing add new doctor");
        try {
            Doctor doctor = modelMapper.map(request, Doctor.class);
            log.debug(doctor.toString());
            doctorRepository.save(doctor);

            log.info("Successfully added new Doctor");
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, modelMapper.map(doctor, DoctorDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while trying to add new doctor. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateById(Long id, DoctorDTO request) {
        log.info("Executing update existing doctor");
        try {

            Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
            if(optionalDoctor.isEmpty()) {
                log.info("Doctor with ID [{}] not found ", id);
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            optionalDoctor.ifPresent(doctor -> {
                doctor = modelMapper.map(request, Doctor.class);
                doctor.setId(id);
                doctorRepository.save(doctor);
            });

            log.info("Successfully updated Doctor with ID : [{}]", id);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, modelMapper.map(optionalDoctor.get(), DoctorDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            log.info("An error occurred while trying to update existing doctor. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteById(Long id) {
        log.info("Executing delete existing doctor");
        try {
            Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
            if (optionalDoctor.isEmpty()) {
                log.info("Doctor with ID : [{}] is not found", id);
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            doctorRepository.delete(optionalDoctor.get());
            log.info("Successfully deleted Doctor with ID : [{}]", id);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.info("An error occurred while trying to delete existing doctor. Error : {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
