package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateExchangeCommand;
import com.techzo.cambiazo.exchanges.domain.model.commands.UpdateExchangeStatusCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;

import java.util.Optional;

public interface IExchangeCommandService {
    Optional<Exchange>handle(CreateExchangeCommand command);

    Optional<Exchange>handle(UpdateExchangeStatusCommand command);
    boolean handleDeleteExchange(Long id);
}
