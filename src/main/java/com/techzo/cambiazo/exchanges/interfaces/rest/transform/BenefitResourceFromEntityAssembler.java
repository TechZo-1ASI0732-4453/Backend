package com.techzo.cambiazo.exchanges.interfaces.rest.transform;


import com.techzo.cambiazo.exchanges.domain.model.entities.Benefit;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.BenefitResource;

public class BenefitResourceFromEntityAssembler {

    public static BenefitResource toResourceFromEntity(Benefit entity) {
        return new BenefitResource(entity.getId(), entity.getDescription(), entity.getPlanId());
    }
}
