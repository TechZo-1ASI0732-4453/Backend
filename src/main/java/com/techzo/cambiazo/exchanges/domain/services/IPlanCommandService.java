package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreatePlanCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Plan;

import java.util.Optional;

public interface IPlanCommandService {
    Optional<Plan> handle(CreatePlanCommand command);
}
