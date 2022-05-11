package com.alterra.miniproject.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private DoctorDTO doctor;
    private FacilityDTO facility;
    private Set<DayOfWeek> workDays;
}
