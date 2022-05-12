package com.alterra.miniproject.domain.dao;

import com.alterra.miniproject.domain.common.BaseDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "M_DOCTOR_DETAIL")
@SQLDelete(sql = "UPDATE M_DOCTOR_DETAIL SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@IdClass(DoctorDetail.DoctorDetailId.class)
public class DoctorDetail extends BaseDAO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DoctorDetailId implements Serializable {
        private static final long serialVersionUID = 8937107533429885898L;
        private Long doctor;
        private Long facility;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Id
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "work_days")
    private Set<DayOfWeek> workDays;
}
