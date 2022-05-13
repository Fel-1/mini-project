package com.alterra.miniproject.service;

import com.alterra.miniproject.constant.AppConstant;
import com.alterra.miniproject.domain.common.ApiResponse;
import com.alterra.miniproject.domain.dao.Facility;
import com.alterra.miniproject.domain.dao.FacilityType;
import com.alterra.miniproject.domain.dao.Location;
import com.alterra.miniproject.domain.dto.FacilityDTO;
import com.alterra.miniproject.domain.dto.FacilityTypeDTO;
import com.alterra.miniproject.domain.dto.LocationDTO;
import com.alterra.miniproject.repository.FacilityRepository;
import com.alterra.miniproject.repository.FacilityTypeRepository;
import com.alterra.miniproject.repository.LocationRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FacilityService.class)
class FacilityServiceTest {

    @MockBean
    private FacilityRepository facilityRepository;

    @MockBean
    private LocationRepository locationRepository;

    @MockBean
    private FacilityTypeRepository facilityTypeRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private FacilityService facilityService;

    @Test
    void getAll_Success_Test() {
        List<Facility> facilities = new ArrayList<>();

        Facility facility = Facility.builder()
                .id(1L)
                .build();
        facilities.add(facility);

        FacilityDTO facilityDTO = FacilityDTO.builder()
                .id(1L)
                .build();

        when(facilityRepository.findAll()).thenReturn(facilities);
        when(modelMapper.map(any(),eq(Facility.class))).thenReturn(facility);
        when(modelMapper.map(any(),eq(FacilityDTO.class))).thenReturn(facilityDTO);

        ResponseEntity<Object> responseEntity = facilityService.getAll();
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        List<FacilityDTO> result = ((List<FacilityDTO>) apiResponse.getData());

        assertEquals(1L, result.get(0).getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getAll_Error_Test() {
        when(facilityRepository.findAll()).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = facilityService.getAll();
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void addNew_Success_Test() {
        Facility facility = Facility.builder()
                .id(1L)
                .name("Fac")
                .build();

        Location location = Location.builder()
                .id(1L)
                .kelurahan("lurah")
                .build();

        FacilityType facilityType = FacilityType.builder()
                .id(1L)
                .type("Tipe")
                .build();

        FacilityDTO facilityDTO = FacilityDTO.builder()
                .id(1L)
                .name("DTO Fac")
                .facilityType(FacilityTypeDTO.builder().id(1L).build())
                .location(LocationDTO.builder().id(1L).build())
                .build();

        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(location));
        when(facilityTypeRepository.findById(anyLong())).thenReturn(Optional.of(facilityType));
        when(modelMapper.map(any(),eq(Facility.class))).thenReturn(facility);
        when(modelMapper.map(any(),eq(FacilityDTO.class))).thenReturn(facilityDTO);
        when(facilityRepository.save(any())).thenReturn(facility);

        ResponseEntity responseEntity = facilityService.addNew(1L, 1L, facilityDTO, null);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("DTO Fac", ((FacilityDTO) apiResponse.getData()).getName());
    }

    @Test
    void addNew_Error_Test() {

        when(facilityTypeRepository.findById(anyLong())).thenThrow(NullPointerException.class);

        ResponseEntity<Object> responseEntity = facilityService.addNew(1L, 1L, FacilityDTO
                .builder()
                .id(1L)
                .location(LocationDTO.builder().id(1L).build())
                .facilityType(FacilityTypeDTO.builder().id(1L).build())
                .build(), null);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void addNew_LocationEmpty_Test() {

        when(facilityTypeRepository.findById(anyLong())).thenReturn(Optional.of(FacilityType
                .builder()
                .id(1L)
                .build()));

        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = facilityService.addNew(1L, 1L, FacilityDTO
                .builder()
                .id(1L)
                .location(LocationDTO.builder().id(1L).build())
                .facilityType(FacilityTypeDTO.builder().id(1L).build())
                .build(), null);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }
    @Test
    void addNew_FacilityTypeEmpty_Test() {

        when(facilityTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = facilityService.addNew(1L, 1L, FacilityDTO
                .builder()
                .id(1L)
                .location(LocationDTO.builder().id(1L).build())
                .facilityType(FacilityTypeDTO.builder().id(1L).build())
                .build(), null);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void updateById_Success_Test() {
        Facility facility = Facility.builder()
                .id(1L)
                .name("Fac")
                .build();

        Location location = Location.builder()
                .id(1L)
                .kelurahan("lurah")
                .build();

        FacilityType facilityType = FacilityType.builder()
                .id(1L)
                .type("Tipe")
                .build();

        FacilityDTO facilityDTO = FacilityDTO.builder()
                .id(1L)
                .name("DTO Fac")
                .facilityType(FacilityTypeDTO.builder().id(1L).build())
                .location(LocationDTO.builder().id(1L).build())
                .build();

        when(facilityRepository.findById(anyLong())).thenReturn(Optional.of(facility));
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(location));
        when(facilityTypeRepository.findById(anyLong())).thenReturn(Optional.of(facilityType));
        when(modelMapper.map(any(),eq(Facility.class))).thenReturn(facility);
        when(modelMapper.map(any(),eq(FacilityDTO.class))).thenReturn(facilityDTO);
        when(facilityRepository.save(any())).thenReturn(facility);

        ResponseEntity responseEntity = facilityService.updateById(1L, facilityDTO);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("DTO Fac", ((FacilityDTO) apiResponse.getData()).getName());
    }

    @Test
    void updateById_FacilityTypeEmpty_Test() {

        when(facilityRepository.findById(anyLong())).thenReturn(Optional.of(Facility
                .builder()
                .id(1L)
                .build()));

        when(facilityTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = facilityService.updateById(1L, FacilityDTO
                .builder()
                .id(1L)
                .location(LocationDTO.builder().id(1L).build())
                .facilityType(FacilityTypeDTO.builder().id(1L).build())
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void updateById_LocationEmpty_Test() {

        when(facilityRepository.findById(anyLong())).thenReturn(Optional.of(Facility
                .builder()
                .id(1L)
                .build()));

        when(facilityTypeRepository.findById(anyLong())).thenReturn(Optional.of(FacilityType
                .builder()
                .id(1L)
                .build()));

        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = facilityService.updateById(1L, FacilityDTO
                .builder()
                .id(1L)
                .location(LocationDTO.builder().id(1L).build())
                .facilityType(FacilityTypeDTO.builder().id(1L).build())
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }
    @Test
    void updateById_FacilityEmpty_Test() {

        when(facilityRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = facilityService.updateById(1L, FacilityDTO
                .builder()
                .id(1L)
                .location(LocationDTO.builder().id(1L).build())
                .facilityType(FacilityTypeDTO.builder().id(1L).build())
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void updateById_Error_Test() {
        when(facilityRepository.findById(anyLong())).thenThrow(NullPointerException.class);

        ResponseEntity<Object> responseEntity = facilityService.updateById(1L,FacilityDTO
                .builder()
                .id(1L)
                .location(LocationDTO.builder().id(1L).build())
                .facilityType(FacilityTypeDTO.builder().id(1L).build())
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void deleteById_Success_Test() {
        when(facilityRepository.findById(anyLong())).thenReturn(Optional.of(Facility.builder()
                .id(1L)
                .build()));

        doNothing().when(facilityRepository).delete(any());

        ResponseEntity<Object> responseEntity = facilityService.deleteById(1L);
        verify(facilityRepository, times(1)).delete(any());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void deleteById_FacilityEmpty_Test() {

        when(facilityRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = facilityService.deleteById(1L);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void deleteById_Error_Test() {
        when(facilityRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = facilityService.deleteById(1L);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }
}