package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.Department;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllDepartmentsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetDepartmentByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IDepartmentQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IDepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentQueryServiceImpl implements IDepartmentQueryService {

    private final IDepartmentRepository departmentRepository;

    public DepartmentQueryServiceImpl(IDepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Optional<Department>handle(GetDepartmentByIdQuery query) {
        return departmentRepository.findById(query.id());
    }

    public List<Department>handle(GetAllDepartmentsQuery query) {
        return departmentRepository.findAll();
    }
}
