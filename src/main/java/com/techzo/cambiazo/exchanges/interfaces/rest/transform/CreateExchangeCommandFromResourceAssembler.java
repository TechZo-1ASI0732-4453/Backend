package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateExchangeCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateExchangeResource;

public class CreateExchangeCommandFromResourceAssembler {
    public static CreateExchangeCommand toCommandFromResource(CreateExchangeResource resource) {
        return new CreateExchangeCommand(resource.productOwnId(), resource.productChangeId(), resource.status());
    }
}
