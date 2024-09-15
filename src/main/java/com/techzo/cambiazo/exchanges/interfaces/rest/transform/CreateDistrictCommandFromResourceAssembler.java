package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateDistrictCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateDistrictResource;

public class CreateDistrictCommandFromResourceAssembler {
    public static CreateDistrictCommand toCommandFromResource(CreateDistrictResource districtResource) {
        return new CreateDistrictCommand(districtResource.name(), districtResource.departmentId());
    }
}
