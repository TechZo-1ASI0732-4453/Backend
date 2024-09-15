package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateFavoriteProductCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateFavoriteProductResource;

public class CreateFavoriteProductCommandFromResourceAssembler {
    public static CreateFavoriteProductCommand toCommandFromResource(CreateFavoriteProductResource resource) {
        return new CreateFavoriteProductCommand(resource.productId(), resource.userId());
    }
}
