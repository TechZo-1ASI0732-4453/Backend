package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;

import com.techzo.cambiazo.exchanges.domain.model.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDistrictRepository extends JpaRepository<District, Long> {
    boolean existsByName(String name);
}
