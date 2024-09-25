package com.techzo.cambiazo.donations.interfaces.rest.transform;

import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CategoryOngResource;

public class CategoryOngResourceFromEntityAssembler {
    public static CategoryOngResource toResourceFromEntity(CategoryOng entity) {
        return new CategoryOngResource(entity.getId(), entity.getName());
    }
}
