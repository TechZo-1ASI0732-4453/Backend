package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateCountryCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Country;

import java.util.Optional;

public interface ICountryCommandService {
    Optional<Country> handle(CreateCountryCommand command);
}
