package com.techzo.cambiazo.donations.interfaces.rest.transform;

import com.techzo.cambiazo.donations.domain.model.commands.CreateSocialNetworkCommand;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CreateSocialNetworkResource;

public class CreateSocialNetworkCommandFromResourceAssembler {

    public static CreateSocialNetworkCommand toCommandFromResource(CreateSocialNetworkResource resource) {
        return new CreateSocialNetworkCommand(resource.name(), resource.url(), resource.ongId());
    }
}
