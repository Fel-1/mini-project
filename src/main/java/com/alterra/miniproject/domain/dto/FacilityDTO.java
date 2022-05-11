package com.alterra.miniproject.domain.dto;

import com.alterra.miniproject.domain.common.ApiResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacilityDTO {

    private Long id;

    private String name;

    private FacilityTypeDTO facilityType;

    private LocationDTO location;

    private String address;

    private String mapUrl;

    private String websiteUrl;

    @JsonIgnore
    public ApiResponse validate() {
        String message;
        if(this.id == null) {
            message = "Id tidak boleh kosong";
        }
        if(name == null){
            message = "Nama tidak boleh kosong";
        }
        return ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .build();

    }

}
