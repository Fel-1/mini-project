package com.alterra.miniproject.service;

import com.alterra.miniproject.constant.AppConstant;
import com.alterra.miniproject.domain.common.ApiResponse;
import com.alterra.miniproject.domain.dao.Location;
import com.alterra.miniproject.domain.dto.LocationDTO;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LocationService.class)
class LocationServiceTest {

    @MockBean
    private LocationRepository locationRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private LocationService locationService;

    @Test
    void getAll_Success_Test() {
        List<Location> locations = new ArrayList<>();

        Location location = Location.builder()
                .id(1L)
                .build();
        locations.add(location);

        LocationDTO locationDTO = LocationDTO.builder()
                .id(1L)
                .build();

        when(locationRepository.findAll()).thenReturn(locations);
        when(modelMapper.map(any(),eq(Location.class))).thenReturn(location);
        when(modelMapper.map(any(),eq(LocationDTO.class))).thenReturn(locationDTO);

        ResponseEntity<Object> responseEntity = locationService.getAll();
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        List<LocationDTO> result = ((List<LocationDTO>) apiResponse.getData());

        assertEquals(1L, result.get(0).getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Test
    void getAll_Error_Test() {
        when(locationRepository.findAll()).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = locationService.getAll();
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void addNew_Success_Test() {
        Location location = Location.builder()
                .id(1L)
                .kota("Depok")
                .build();

        LocationDTO locationDTO = LocationDTO.builder()
                .id(1L)
                .kota("Depok")
                .build();

        when(modelMapper.map(any(),eq(Location.class))).thenReturn(location);
        when(modelMapper.map(any(),eq(LocationDTO.class))).thenReturn(locationDTO);
        when(locationRepository.save(any())).thenReturn(location);

        ResponseEntity responseEntity = locationService.addNew(locationDTO);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Depok", ((LocationDTO) apiResponse.getData()).getKota());

    }
    @Test
    void addNew_Error_Test() {
        when(locationRepository.save(any())).thenThrow(NullPointerException.class);

        ResponseEntity<Object> responseEntity = locationService.addNew(LocationDTO
                .builder()
                .id(1L)
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void updateById_Success_Test() {
        Location location = Location.builder()
                .id(1L)
                .kota("Depok")
                .build();

        LocationDTO locationDTO = LocationDTO.builder()
                .id(1L)
                .kota("Depok")
                .build();

        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(location));
        when(modelMapper.map(any(),eq(Location.class))).thenReturn(location);
        when(modelMapper.map(any(),eq(LocationDTO.class))).thenReturn(locationDTO);
        when(locationRepository.save(any())).thenReturn(location);

        ResponseEntity responseEntity = locationService.updateById(1L, locationDTO);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Depok", ((LocationDTO) apiResponse.getData()).getKota());
    }
    @Test
    void updateById_LocationEmpty_Test() {

        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = locationService.updateById(1L, LocationDTO
                .builder()
                .id(1L)
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void updateById_Error_Test() {
        when(locationRepository.findById(anyLong())).thenThrow(NullPointerException.class);

        ResponseEntity<Object> responseEntity = locationService.updateById(1L,LocationDTO
                .builder()
                .id(1L)
                .build());

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void deleteById_Success_Test() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(Location
                .builder()
                .id(1L)
                .build()));

        doNothing().when(locationRepository).delete(any());

        ResponseEntity<Object> responseEntity = locationService.deleteById(1L);
        verify(locationRepository, times(1)).delete(any());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
    @Test
    void deleteById_LocationEmpty_Test() {

        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = locationService.deleteById(1L);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), apiResponse.getStatus().getCode());
    }

    @Test
    void deleteById_Error_Test() {
        when(locationRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = locationService.deleteById(1L);

        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), apiResponse.getStatus().getCode());
    }

}