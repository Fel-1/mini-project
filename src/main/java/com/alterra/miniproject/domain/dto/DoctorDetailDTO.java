package com.alterra.miniproject.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorDetailDTO implements Serializable {

    private static final long serialVersionUID = -7188902159788748381L;

    @Schema(type = "DoctorDTO", hidden = true)
    private DoctorDTO doctor;

    @Schema(type = "FacilityDTO", hidden = true)
    private FacilityDTO facility;

    @Schema(type = "Set<DayOfWeek>")
    private Set<DayOfWeek> workDays;
}
