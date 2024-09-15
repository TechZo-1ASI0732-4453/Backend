package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.entities.Plan;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.PlanResource;

public class PlanResourceFromEntityAssembler {

    public static PlanResource toResourceFromEntity(Plan entity) {
        return new PlanResource(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice());
    }
}
