package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.Department;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllDepartmentsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetDepartmentByIdQuery;

import java.util.List;
import java.util.Optional;

public interface IDepartmentQueryService {

    Optional<Department>handle(GetDepartmentByIdQuery query);

    List<Department>handle(GetAllDepartmentsQuery query);
}
