package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.entities.Country;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CountryResource;

public class CountryResourceFromEntityAssembler {
    public static CountryResource toResourceFromEntity(Country entity) {
        return new CountryResource(entity.getId(), entity.getName());
    }
}
