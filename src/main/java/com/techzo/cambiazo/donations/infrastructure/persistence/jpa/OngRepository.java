package com.techzo.cambiazo.donations.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;

import java.util.List;
import java.util.Optional;

@Repository
public interface OngRepository extends JpaRepository<Ong, Long> {
    Optional<Ong> findByName(String name);

    Optional<Ong> findByEmail(String email);

    List<Ong> findByCategoryOngId(CategoryOng id);

    @Query("SELECT DISTINCT o FROM Ong o " +
            "LEFT JOIN FETCH o.categoryOngId " +
            "LEFT JOIN FETCH o.projects " +
            "LEFT JOIN FETCH o.accountNumbers " +
            "LEFT JOIN FETCH o.socialNetworks " +
            "WHERE o.id = :id")
    Optional<Ong> findOneWithRelations(Long id);

    List<Ong>findByNameContaining(String name);

}

