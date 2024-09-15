package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateSubscriptionCommand;
import com.techzo.cambiazo.exchanges.domain.model.commands.UpdateSubscriptionCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Subscription;

import java.util.Optional;

public interface ISubscriptionCommandService {
    Optional<Subscription>handle(CreateSubscriptionCommand command);

    Optional<Subscription>handle(UpdateSubscriptionCommand command);
}
