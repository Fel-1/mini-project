package com.alterra.miniproject.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema( type = "long", example = "1", description = "ID lokasi", hidden = true)
    private Long id;

    @Schema( type = "string", example = "Pancoran Mas", description = "Nama kelurahan")
    private String kelurahan;

    @Schema( type = "string", example = "Pancoran Mas", description = "Nama kecamatan")
    private String kecamatan;

    @Schema( type = "string", example = "Depok", description = "Nama kota")
    private String kota;

    @Schema( type = "string", example = "Jawa Barat", description = "Nama provinsi")
    private String provinsi;

}
