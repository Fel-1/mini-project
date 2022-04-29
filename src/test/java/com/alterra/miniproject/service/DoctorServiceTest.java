package com.alterra.miniproject.service;

import com.alterra.miniproject.constant.AppConstant;
import com.alterra.miniproject.domain.common.ApiResponse;
import com.alterra.miniproject.domain.dao.Doctor;
import com.alterra.miniproject.domain.dto.DoctorDTO;
import com.alterra.miniproject.repository.DoctorRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DoctorService.class)
class DoctorServiceTest {
    @MockBean
    private DoctorRepository doctorRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private DoctorService doctorService;

    @Test
    void getAll_Success_Test() {
        List<Doctor> doctors = new ArrayList<>();

        Doctor doctor = Doctor.builder()
                .id(1L)
                .build();
        doctors.add(doctor);

        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .build();

        when(doctorRepository.findAll()).thenReturn(doctors);
        when(modelMapper.map(any(),eq(Doctor.class))).thenReturn(doctor);
        when(modelMapper.map(any(),eq(DoctorDTO.class))).thenReturn(doctorDTO);

        ResponseEntity<Object> responseEntity = doctorService.getAll();
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        List<DoctorDTO> result = ((List<DoctorDTO>) apiResponse.getData());

        assertEquals(1L, result.get(0).getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Test
    void getAll_Error_Test() {
        when(doctorRepository.findAll()).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = doctorService.getAll();
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void addNew_Success_Test() {
        Doctor doctor = Doctor.builder()
                .id(1L)
                .name("Dokter A")
                .build();

        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .name("Dokter A")
                .build();

        when(modelMapper.map(any(),eq(Doctor.class))).thenReturn(doctor);
        when(modelMapper.map(any(),eq(DoctorDTO.class))).thenReturn(doctorDTO);
        when(doctorRepository.save(any())).thenReturn(doctor);

        ResponseEntity responseEntity = doctorService.addNew(doctorDTO);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Dokter A", ((DoctorDTO) apiResponse.getData()).getName());

    }
    @Test
    void addNew_Error_Test() {
        when(doctorRepository.save(any())).thenThrow(NullPointerException.class);

        ResponseEntity<Object> responseEntity = doctorService.addNew(DoctorDTO
                .builder()
                .id(1L)
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void updateById_Success_Test() {
        Doctor doctor = Doctor.builder()
                .id(1L)
                .name("Dokter A")
                .build();

        DoctorDTO doctorDTO = DoctorDTO.builder()
                .id(1L)
                .name("Dokter A")
                .build();

        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));
        when(modelMapper.map(any(),eq(Doctor.class))).thenReturn(doctor);
        when(modelMapper.map(any(),eq(DoctorDTO.class))).thenReturn(doctorDTO);
        when(doctorRepository.save(any())).thenReturn(doctor);

        ResponseEntity responseEntity = doctorService.updateById(1L, doctorDTO);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Dokter A", ((DoctorDTO) apiResponse.getData()).getName());
    }
    @Test
    void updateById_DoctorEmpty_Test() {

        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = doctorService.updateById(1L, DoctorDTO
                .builder()
                .id(1L)
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void updateById_Error_Test() {
        when(doctorRepository.findById(anyLong())).thenThrow(NullPointerException.class);

        ResponseEntity<Object> responseEntity = doctorService.updateById(1L,DoctorDTO
                .builder()
                .id(1L)
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void deleteById_Success_Test() {
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(Doctor
                .builder()
                .id(1L)
                .build()));

        doNothing().when(doctorRepository).delete(any());

        ResponseEntity<Object> responseEntity = doctorService.deleteById(1L);
        verify(doctorRepository, times(1)).delete(any());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
    @Test
    void deleteById_DoctorEmpty_Test() {

        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = doctorService.deleteById(1L);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void deleteById_Error_Test() {
        when(doctorRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = doctorService.deleteById(1L);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }
}