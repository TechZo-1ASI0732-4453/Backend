package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateDepartmentCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateDepartmentResource;

public class CreateDepartmentCommandFromResourceAssembler {
    public static CreateDepartmentCommand toCommandFromResource(CreateDepartmentResource resource) {
        return new CreateDepartmentCommand(resource.name(), resource.countryId());
    }
}
