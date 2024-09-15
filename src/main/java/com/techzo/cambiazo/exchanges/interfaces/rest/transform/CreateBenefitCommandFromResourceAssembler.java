package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateBenefitCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateBenefitResource;


public class CreateBenefitCommandFromResourceAssembler {

    public static CreateBenefitCommand toCommandFromResource(CreateBenefitResource resource) {
        return new CreateBenefitCommand(resource.description(), resource.planId());
    }
}
