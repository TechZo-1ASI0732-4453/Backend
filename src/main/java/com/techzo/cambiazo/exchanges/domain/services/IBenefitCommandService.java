package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateBenefitCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Benefit;

import java.util.Optional;

public interface IBenefitCommandService {

    Optional<Benefit>handle(CreateBenefitCommand command);
}
