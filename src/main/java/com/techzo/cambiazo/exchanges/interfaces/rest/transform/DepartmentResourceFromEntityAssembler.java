package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.entities.Department;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.DepartmentResource;

public class DepartmentResourceFromEntityAssembler {
    public static DepartmentResource toResourceFromEntity(Department department) {
        return new DepartmentResource(department.getId(), department.getName(), department.getCountryId());
    }
}
