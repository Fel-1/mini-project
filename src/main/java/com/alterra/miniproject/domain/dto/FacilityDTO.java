package com.alterra.miniproject.domain.dto;

import com.alterra.miniproject.domain.dao.FacilityTypeDAO;
import com.alterra.miniproject.domain.dao.LocationDAO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacilityDTO {

    private Long id;

    private String name;

    private FacilityTypeDTO facilityTypeDTO;

    private LocationDTO locationDTO;

    private String address;

    private String mapUrl;

    private String websiteUrl;

}
