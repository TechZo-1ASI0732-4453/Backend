package com.techzo.cambiazo.donations.domain.services;

import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.commands.CreateOngCommand;
import com.techzo.cambiazo.donations.domain.model.commands.UpdateOngCommand;

import java.util.Optional;

public interface OngCommandService {
    Optional<Ong>handle(CreateOngCommand command);
    Optional<Ong>handle(UpdateOngCommand command);
    boolean handleDeleteOng(Long id);
}
