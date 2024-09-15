package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.Plan;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllPlansQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetPlanByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IPlanQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PlanQueryServiceImpl implements IPlanQueryService {

    private final IPlanRepository planRepository;

    public PlanQueryServiceImpl(IPlanRepository planRepository){
        this.planRepository=planRepository;
    }


    @Override
    public Optional<Plan> handle(GetPlanByIdQuery query) {
        return this.planRepository.findById(query.id());
    }

    @Override
    public List<Plan> handle(GetAllPlansQuery query) {
        return this.planRepository.findAll();
    }
}
