package com.alterra.miniproject.repository;

import com.alterra.miniproject.domain.dao.DoctorDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorDetailRepository extends JpaRepository<DoctorDetail, Long> {
    List<DoctorDetail> findAllByDoctor_Id(Long doctorId);
}
