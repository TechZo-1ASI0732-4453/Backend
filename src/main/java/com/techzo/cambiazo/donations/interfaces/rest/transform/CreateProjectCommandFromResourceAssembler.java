package com.techzo.cambiazo.donations.interfaces.rest.transform;


import com.techzo.cambiazo.donations.domain.model.commands.CreateProjectCommand;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CreateProjectResource;

public class CreateProjectCommandFromResourceAssembler {
    public static CreateProjectCommand toCommandFromResource(CreateProjectResource resource) {
        return new CreateProjectCommand(resource.name(), resource.description(), resource.ongId());
    }
}
