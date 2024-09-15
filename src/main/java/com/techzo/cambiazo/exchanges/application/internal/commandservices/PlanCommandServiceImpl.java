package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreatePlanCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Plan;
import com.techzo.cambiazo.exchanges.domain.services.IPlanCommandService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IPlanRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanCommandServiceImpl implements IPlanCommandService {

    private final IPlanRepository planRepository;

    public PlanCommandServiceImpl(IPlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public Optional<Plan>handle(CreatePlanCommand command){
        if(planRepository.existsByName(command.name())){
            throw new IllegalArgumentException("Plan with same name already exists");
        }
        var plan = new Plan(command);
        var createdPlan = planRepository.save(plan);
        return Optional.of(createdPlan);
    }

}
