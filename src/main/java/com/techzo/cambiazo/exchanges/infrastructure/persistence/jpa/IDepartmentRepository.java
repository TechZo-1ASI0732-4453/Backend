package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;

import com.techzo.cambiazo.exchanges.domain.model.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);
}
