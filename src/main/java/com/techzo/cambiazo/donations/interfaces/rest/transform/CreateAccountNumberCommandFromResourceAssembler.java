package com.techzo.cambiazo.donations.interfaces.rest.transform;

import com.techzo.cambiazo.donations.domain.model.commands.CreateAccountNumberCommand;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CreateAccountNumberResource;

public class CreateAccountNumberCommandFromResourceAssembler {

    public static CreateAccountNumberCommand toCommandFromResource(CreateAccountNumberResource resource) {
        return new CreateAccountNumberCommand(resource.name(), resource.cci(), resource.account(), resource.ongId());
    }
}
