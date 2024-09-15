package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateDistrictCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.District;

import java.util.Optional;

public interface IDistrictCommandService {
    Optional<District>handle(CreateDistrictCommand command);
}
