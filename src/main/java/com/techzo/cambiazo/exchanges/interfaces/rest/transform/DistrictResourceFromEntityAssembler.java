package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.entities.District;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.DistrictResource;

public class DistrictResourceFromEntityAssembler {
    public static DistrictResource toResourceFromEntity(District district) {
        return new DistrictResource(district.getId(), district.getName(), district.getDepartmentId());
    }
}
