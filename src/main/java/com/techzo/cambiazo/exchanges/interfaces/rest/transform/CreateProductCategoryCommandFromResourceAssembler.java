package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateProductCategoryCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateProductCategoryResource;

public class CreateProductCategoryCommandFromResourceAssembler {
    public static CreateProductCategoryCommand toCommandFromResource(CreateProductCategoryResource resource) {
        return new CreateProductCategoryCommand(resource.name());
    }
}
