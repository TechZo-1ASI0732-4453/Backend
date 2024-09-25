package com.techzo.cambiazo.donations.interfaces.rest.transform;

import com.techzo.cambiazo.donations.domain.model.commands.UpdateCategoryOngCommand;
import com.techzo.cambiazo.donations.interfaces.rest.resources.UpdateCategoryOngResource;

public class UpdateCategoryOngCommandFromResourceAssembler {

    public static UpdateCategoryOngCommand toCommandFromResource(Long categoryId, UpdateCategoryOngResource resource) {
        return new UpdateCategoryOngCommand(categoryId, resource.name());
    }
}
