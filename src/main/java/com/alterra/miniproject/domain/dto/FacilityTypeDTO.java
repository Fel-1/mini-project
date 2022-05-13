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
public class FacilityTypeDTO {

    @Schema( type = "long", example = "1", description = "ID tipe fasilitas kesehatan", hidden = true)
    private Long id;

    @Schema( type = "string", example = "Rumah Sakit", description = "Tipe fasilitas")
    private String type;

}
