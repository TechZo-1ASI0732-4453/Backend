package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;

import com.techzo.cambiazo.exchanges.domain.model.entities.Benefit;
import com.techzo.cambiazo.exchanges.domain.model.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBenefitRepository extends JpaRepository<Benefit, Long>{


    List<Benefit>findBenefitsByPlanId(Plan planId);
}
