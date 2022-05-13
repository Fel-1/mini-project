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
public class FacilityDTO {

    @Schema( type = "long", example = "1", description = "ID Fasilitas", hidden = true)
    private Long id;
    @Schema( type = "string", example = "Rumah Sakit Bakti Yudha", description = "Nama fasilitas kesehatan")
    private String name;

    @Schema(hidden = true)
    private FacilityTypeDTO facilityType;

    @Schema(hidden = true)
    private LocationDTO location;

    @Schema( type = "string", example = "Jl. Raya Sawangan No.2A", description = "Alamat fasilitas kesehatan")
    private String address;

    @Schema( type = "string", example = "https://goo.gl/maps/CDW4h3vGRvXcgRJ16", description = "URL google map fasilitas kesehatan")
    private String mapUrl;

    @Schema( type = "string", example = "https://www.rsbhaktiyudha.co.id/", description = "URL website resmi fasilitas kesehatan")
    private String websiteUrl;


}
