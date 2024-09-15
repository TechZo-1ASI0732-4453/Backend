package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateProductCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateProductResource;

public class CreateProductCommandFromResourceAssembler {

    public static CreateProductCommand toCommandFromResource(CreateProductResource resource) {
        return new CreateProductCommand(resource.name(), resource.description(), resource.desiredObject(), resource.price(), resource.image(), resource.boost(), resource.available(), resource.productCategoryId(), resource.userId(), resource.districtId());
    }
}
