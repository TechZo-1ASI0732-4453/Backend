package com.techzo.cambiazo.donations.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;

import java.util.List;
import java.util.Optional;

@Repository
public interface OngRepository extends JpaRepository<Ong, Long>{
    Optional<Ong>findByName(String name);
    Optional<Ong>findByEmail(String email);
    List<Ong>findByCategoryOngId(CategoryOng id);

    List<Ong>findByNameContaining(String name);

}
