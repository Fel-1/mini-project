package com.alterra.miniproject.repository;

import com.alterra.miniproject.domain.dao.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
