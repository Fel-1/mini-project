package com.alterra.miniproject.repository;

import com.alterra.miniproject.domain.dao.DoctorDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorDetailRepository extends JpaRepository<DoctorDetail, Long> {
    List<DoctorDetail> findAllByDoctor_Id(Long doctorId);
    Optional<DoctorDetail> findByDoctor_IdAndFacility_Id(Long doctorId, Long facilityId);
}
