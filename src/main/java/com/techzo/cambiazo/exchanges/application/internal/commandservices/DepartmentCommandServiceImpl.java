package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateDepartmentCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Country;
import com.techzo.cambiazo.exchanges.domain.model.entities.Department;
import com.techzo.cambiazo.exchanges.domain.services.IDepartmentCommandService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.ICountryRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IDepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentCommandServiceImpl implements IDepartmentCommandService {
    private final IDepartmentRepository departmentRepository;

    private final ICountryRepository countryRepository;


    public DepartmentCommandServiceImpl(IDepartmentRepository departmentRepository, ICountryRepository countryRepository) {
        this.departmentRepository = departmentRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public Optional<Department>handle(CreateDepartmentCommand command) {
        Country country = countryRepository.findById(command.countryId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));

        if (departmentRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Department with same name already exists");
        }
        var department = new Department(command, country);
        var createdDepartment = departmentRepository.save(department);
        return Optional.of(createdDepartment);
    }
}
