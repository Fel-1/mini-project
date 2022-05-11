package com.alterra.miniproject.repository;

import com.alterra.miniproject.domain.dao.FacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityTypeRepository extends JpaRepository<FacilityType, Long> {
}
