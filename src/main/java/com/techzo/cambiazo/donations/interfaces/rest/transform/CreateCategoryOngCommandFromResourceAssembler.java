package com.techzo.cambiazo.donations.interfaces.rest.transform;

import com.techzo.cambiazo.donations.domain.model.commands.CreateCategoryOngCommand;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CreateCategoryOngResource;

public class CreateCategoryOngCommandFromResourceAssembler {
    public static CreateCategoryOngCommand toCommandFromResource(CreateCategoryOngResource resource) {
        return new CreateCategoryOngCommand(resource.name());
    }
}
