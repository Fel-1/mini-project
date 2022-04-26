package com.alterra.miniproject.domain.dto;

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
public class LocationDTO {

    private Long id;

    private String kelurahan;

    private String kecamatan;

    private String kota;

    private String provinsi;

}
