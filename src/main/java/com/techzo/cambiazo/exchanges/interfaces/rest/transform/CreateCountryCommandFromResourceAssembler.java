package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateCountryCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateCountryResource;

public class CreateCountryCommandFromResourceAssembler {
    public static CreateCountryCommand toCommandFromResource(CreateCountryResource resource) {
        return new CreateCountryCommand(resource.name());
    }
}
