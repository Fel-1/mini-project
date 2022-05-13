package com.alterra.miniproject.service;

import com.alterra.miniproject.constant.AppConstant;
import com.alterra.miniproject.domain.common.ApiResponse;
import com.alterra.miniproject.domain.dao.Doctor;
import com.alterra.miniproject.domain.dao.DoctorDetail;
import com.alterra.miniproject.domain.dao.Facility;
import com.alterra.miniproject.domain.dto.DoctorDTO;
import com.alterra.miniproject.domain.dto.DoctorDetailDTO;
import com.alterra.miniproject.domain.dto.FacilityDTO;
import com.alterra.miniproject.domain.dto.SingleDoctorRequest;
import com.alterra.miniproject.repository.DoctorDetailRepository;
import com.alterra.miniproject.repository.DoctorRepository;
import com.alterra.miniproject.repository.FacilityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DoctorDetailService.class)
class DoctorDetailServiceTest {

    @MockBean
    private DoctorDetailRepository doctorDetailRepository;

    @MockBean
    private FacilityRepository facilityRepository;

    @MockBean
    private DoctorRepository doctorRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private DoctorDetailService doctorDetailService;

    @Test
    void getAllDoctorDetail_Success_Test() {
        List<DoctorDetail> doctorDetails = new ArrayList<>();

        Doctor doctor = Doctor.builder()
                .id(1L)
                .build();
        Facility facility = Facility.builder()
                .id(1L)
                .build();
        DoctorDetail doctorDetail = DoctorDetail.builder()
                .doctor(doctor)
                .facility(facility)
                .build();
        doctorDetails.add(doctorDetail);
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .build();
        FacilityDTO facilityDTO = FacilityDTO.builder()
                .id(1L)
                .build();
        DoctorDetailDTO doctorDetailDTO = DoctorDetailDTO.builder()
                .doctor(doctorDTO)
                .facility(facilityDTO)
                .build();

        when(doctorDetailRepository.findAll()).thenReturn(doctorDetails);
        when(modelMapper.map(any(),eq(DoctorDetailDTO.class))).thenReturn(doctorDetailDTO);

        ResponseEntity<Object> responseEntity = doctorDetailService.getAllDoctorDetail();
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        List<DoctorDetailDTO> result = ((List<DoctorDetailDTO>) apiResponse.getData());


        assertEquals(1L, result.get(0).getDoctor().getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void getAllDoctorDetail_Error_Test() {
        when(doctorDetailRepository.findAll()).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = doctorDetailService.getAllDoctorDetail();
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void addNewDetail_Success_Test() {
        Doctor doctor = Doctor.builder()
                .id(1L)
                .build();
        Facility facility = Facility.builder()
                .id(1L)
                .build();
        DoctorDetail doctorDetail = DoctorDetail.builder()
                .doctor(doctor)
                .facility(facility)
                .build();
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .build();
        FacilityDTO facilityDTO = FacilityDTO.builder()
                .id(1L)
                .build();
        DoctorDetailDTO doctorDetailDTO = DoctorDetailDTO.builder()
                .doctor(doctorDTO)
                .facility(facilityDTO)
                .build();
        when(facilityRepository.findById(anyLong())).thenReturn(Optional.of(facility));
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));
        when(modelMapper.map(any(),eq(DoctorDetail.class))).thenReturn(doctorDetail);
        when(doctorDetailRepository.save(any())).thenReturn(doctorDetail);
        when(modelMapper.map(any(),eq(DoctorDetailDTO.class))).thenReturn(doctorDetailDTO);

        ResponseEntity responseEntity = doctorDetailService
                .addNewDetail(1L, 1L,doctorDetailDTO, null);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, ((DoctorDetailDTO) apiResponse.getData()).getDoctor().getId());
    }
    @Test
    void addNewDetail_Error_Test() {
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .build();
        FacilityDTO facilityDTO = FacilityDTO.builder()
                .id(1L)
                .build();
        DoctorDetailDTO doctorDetailDTO = DoctorDetailDTO.builder()
                .doctor(doctorDTO)
                .facility(facilityDTO)
                .build();
        when(facilityRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = doctorDetailService
                .addNewDetail(1L, 1L,doctorDetailDTO, null);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }
    @Test
    void addNewDetail_FacilityEmpty_Test() {
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .build();
        FacilityDTO facilityDTO = FacilityDTO.builder()
                .id(1L)
                .build();
        DoctorDetailDTO doctorDetailDTO = DoctorDetailDTO.builder()
                .doctor(doctorDTO)
                .facility(facilityDTO)
                .build();
        when(facilityRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = doctorDetailService
                .addNewDetail(1L, 1L, doctorDetailDTO, null);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }
    @Test
    void addNewDetail_DoctorEmpty_Test() {
        Facility facility = Facility.builder()
                .id(1L)
                .build();
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .build();
        FacilityDTO facilityDTO = FacilityDTO.builder()
                .id(1L)
                .build();
        DoctorDetailDTO doctorDetailDTO = DoctorDetailDTO.builder()
                .doctor(doctorDTO)
                .facility(facilityDTO)
                .build();
        when(facilityRepository.findById(anyLong())).thenReturn(Optional.of(facility));
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = doctorDetailService
                .addNewDetail(1L, 1L, doctorDetailDTO, null);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }
    @Test
    void updateWorkDays_Success_Test() {
        DoctorDetail doctorDetail = DoctorDetail.builder()
                .build();
        DoctorDetailDTO doctorDetailDTO = DoctorDetailDTO.builder()
                .build();

        when(doctorDetailRepository.findByDoctor_IdAndFacility_Id(anyLong(), anyLong()))
                .thenReturn(Optional.of(doctorDetail));
        when(doctorDetailRepository.save(any())).thenReturn(doctorDetail);
        ResponseEntity<Object> responseEntity = doctorDetailService
                .updateWorkDays(1L, 1L, doctorDetailDTO);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void updateWorkDays_DoctorDetailEmpty_Test() {
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .build();
        FacilityDTO facilityDTO = FacilityDTO.builder()
                .id(1L)
                .build();
        DoctorDetailDTO doctorDetailDTO = DoctorDetailDTO.builder()
                .doctor(doctorDTO)
                .facility(facilityDTO)
                .build();
        when(doctorDetailRepository.findByDoctor_IdAndFacility_Id(anyLong(), anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = doctorDetailService
                .updateWorkDays(1L, 1L, doctorDetailDTO);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }
    @Test
    void updateWorkDays_Error_Test() {
        DoctorDetailDTO doctorDetailDTO = DoctorDetailDTO.builder()
                .build();
        when(doctorDetailRepository.findByDoctor_IdAndFacility_Id(anyLong(), anyLong())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = doctorDetailService
                .updateWorkDays(1L, 1L, doctorDetailDTO);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }
    @Test
    void deleteDoctorDetail_Success_Test() {
        when(doctorDetailRepository.findByDoctor_IdAndFacility_Id(anyLong(), anyLong()))
                .thenReturn(Optional.of(DoctorDetail
                    .builder()
                    .build()));

        doNothing().when(doctorDetailRepository).delete(any());

        ResponseEntity<Object> responseEntity = doctorDetailService.deleteDoctorDetail(1L, 1L);
        verify(doctorDetailRepository, times(1)).delete(any());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
    @Test
    void deleteById_DoctorEmpty_Test() {

        when(doctorDetailRepository.findByDoctor_IdAndFacility_Id(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = doctorDetailService.deleteDoctorDetail(1L, 1L);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void deleteById_Error_Test() {
        when(doctorDetailRepository.findByDoctor_IdAndFacility_Id(anyLong(), anyLong()))
                .thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = doctorDetailService.deleteDoctorDetail(1L,1L);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }
}