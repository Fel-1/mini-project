package com.alterra.miniproject.service;

import com.alterra.miniproject.constant.AppConstant;
import com.alterra.miniproject.domain.common.ApiResponse;
import com.alterra.miniproject.domain.dao.Facility;
import com.alterra.miniproject.domain.dao.FacilityType;
import com.alterra.miniproject.domain.dto.FacilityDTO;
import com.alterra.miniproject.domain.dto.FacilityTypeDTO;
import com.alterra.miniproject.domain.dto.LocationDTO;
import com.alterra.miniproject.repository.FacilityTypeRepository;
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
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FacilityTypeService.class)
class FacilityTypeServiceTest {

    @MockBean
    private FacilityTypeRepository facilityTypeRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private FacilityTypeService facilityTypeService;

    @Test
    void getAll_Success_Test() {
        List<FacilityType> facilityTypes = new ArrayList<>();

        FacilityType facilityType = FacilityType.builder()
                .id(1L)
                .build();
        facilityTypes.add(facilityType);

        FacilityTypeDTO facilityTypeDTO = FacilityTypeDTO.builder()
                .id(1L)
                .build();

        when(facilityTypeRepository.findAll()).thenReturn(facilityTypes);
        when(modelMapper.map(any(),eq(FacilityType.class))).thenReturn(facilityType);
        when(modelMapper.map(any(),eq(FacilityTypeDTO.class))).thenReturn(facilityTypeDTO);

        ResponseEntity<Object> responseEntity = facilityTypeService.getAll();
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        List<FacilityTypeDTO> result = ((List<FacilityTypeDTO>) apiResponse.getData());

        assertEquals(1L, result.get(0).getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Test
    void getAll_Error_Test() {
        when(facilityTypeRepository.findAll()).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = facilityTypeService.getAll();
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void addNew_Success_Test() {
        FacilityType facilityType = FacilityType.builder()
                .id(1L)
                .type("Tipe")
                .build();

        FacilityTypeDTO facilityTypeDTO = FacilityTypeDTO.builder()
                .id(1L)
                .type("Tipe")
                .build();

        when(modelMapper.map(any(),eq(FacilityType.class))).thenReturn(facilityType);
        when(modelMapper.map(any(),eq(FacilityTypeDTO.class))).thenReturn(facilityTypeDTO);
        when(facilityTypeRepository.save(any())).thenReturn(facilityType);

        ResponseEntity responseEntity = facilityTypeService.addNew(facilityTypeDTO);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Tipe", ((FacilityTypeDTO) apiResponse.getData()).getType());

    }
    @Test
    void addNew_Error_Test() {
        when(facilityTypeRepository.save(any())).thenThrow(NullPointerException.class);

        ResponseEntity<Object> responseEntity = facilityTypeService.addNew(FacilityTypeDTO
                .builder()
                .id(1L)
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void updateById_Success_Test() {
        FacilityType facilityType = FacilityType.builder()
                .id(1L)
                .type("Tipe")
                .build();

        FacilityTypeDTO facilityTypeDTO = FacilityTypeDTO.builder()
                .id(1L)
                .type("Tipe")
                .build();

        when(facilityTypeRepository.findById(anyLong())).thenReturn(Optional.of(facilityType));
        when(modelMapper.map(any(),eq(FacilityType.class))).thenReturn(facilityType);
        when(modelMapper.map(any(),eq(FacilityTypeDTO.class))).thenReturn(facilityTypeDTO);
        when(facilityTypeRepository.save(any())).thenReturn(facilityType);

        ResponseEntity responseEntity = facilityTypeService.updateById(1L, facilityTypeDTO);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Tipe", ((FacilityTypeDTO) apiResponse.getData()).getType());
    }
    @Test
    void updateById_FacilityTypeEmpty_Test() {

        when(facilityTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = facilityTypeService.updateById(1L, FacilityTypeDTO
                .builder()
                .id(1L)
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void updateById_Error_Test() {
        when(facilityTypeRepository.findById(anyLong())).thenThrow(NullPointerException.class);

        ResponseEntity<Object> responseEntity = facilityTypeService.updateById(1L,FacilityTypeDTO
                .builder()
                .id(1L)
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void deleteById_Success_Test() {
        when(facilityTypeRepository.findById(anyLong())).thenReturn(Optional.of(FacilityType
                .builder()
                .id(1L)
                .build()));

        doNothing().when(facilityTypeRepository).delete(any());

        ResponseEntity<Object> responseEntity = facilityTypeService.deleteById(1L);
        verify(facilityTypeRepository, times(1)).delete(any());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
    @Test
    void deleteById_FacilityTypeEmpty_Test() {

        when(facilityTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = facilityTypeService.deleteById(1L);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void deleteById_Error_Test() {
        when(facilityTypeRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = facilityTypeService.deleteById(1L);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }
}