package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateDepartmentCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Department;

import java.util.Optional;

public interface IDepartmentCommandService {

    Optional<Department>handle(CreateDepartmentCommand command);
}
