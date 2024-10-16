package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.entities.FavoriteProduct;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.FavoriteProductResource;

public class FavoriteProductResourceFromEntityAssembler {
    public static FavoriteProductResource toResourceFromEntity(FavoriteProduct entity) {
        return new FavoriteProductResource(entity.getId(), entity.getProductId(), entity.getUserId());
    }
}
