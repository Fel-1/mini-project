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
public class DoctorDTO {

    @Schema( type = "long", example = "1", description = "ID dokter")
    private Long id;

    @Schema( type = "string", example = "Clark Kent", description = "Nama dokter")
    private String name;

    @Schema( type = "int", example = "35", description = "Umur dokter")
    private Integer age;

    @Schema( type = "character", example = "L", description = "Jenis kelamin dokter")
    private Character gender;

    @Schema( type = "string", example = "Kulit", description = "Spesialitas dokter")
    private String speciality;

    @Schema( type = "int", example = "3", description = "Pengalaman dokter dalam unit tahun")
    private Integer experience;
}
