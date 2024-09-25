package com.techzo.cambiazo.donations.interfaces.rest.transform;

import com.techzo.cambiazo.donations.domain.model.commands.CreateOngCommand;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CreateOngResource;

public class CreateOngCommandFromResourceAssembler {
    public static CreateOngCommand toCommandFromResource(CreateOngResource resource){
        return new CreateOngCommand(
                resource.name(),
                resource.type(),
                resource.aboutUs(),
                resource.missionAndVision(),
                resource.supportForm(),
                resource.address(),
                resource.email(),
                resource.phone(),
                resource.logo(),
                resource.website(),
                resource.schedule(),
                resource.categoryOngId()
        );
    }
}
