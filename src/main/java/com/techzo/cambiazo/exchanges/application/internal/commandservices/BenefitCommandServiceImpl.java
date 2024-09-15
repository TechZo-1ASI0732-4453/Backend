package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateBenefitCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Benefit;
import com.techzo.cambiazo.exchanges.domain.model.entities.Plan;
import com.techzo.cambiazo.exchanges.domain.services.IBenefitCommandService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IBenefitRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IPlanRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BenefitCommandServiceImpl implements IBenefitCommandService {

    private final IBenefitRepository benefitRepository;

    private final IPlanRepository planRepository;


    public BenefitCommandServiceImpl(IBenefitRepository benefitRepository, IPlanRepository planRepository) {
        this.benefitRepository = benefitRepository;
        this.planRepository = planRepository;
    }


    @Override
    public Optional<Benefit> handle(CreateBenefitCommand command) {
        Plan plan = planRepository.findById(command.planId()).orElseThrow(() -> new IllegalArgumentException("Plan with same id already exists"));
        var benefit = new Benefit(command, plan);
        benefitRepository.save(benefit);
        return Optional.of(benefit);
    }
}
