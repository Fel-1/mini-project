package com.alterra.miniproject.repository;

import com.alterra.miniproject.domain.dao.FacilityDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<FacilityDAO, Long> {

}
