package com.techzo.cambiazo.donations.domain.services;


import com.techzo.cambiazo.donations.domain.model.aggregates.Project;
import com.techzo.cambiazo.donations.domain.model.commands.CreateProjectCommand;

import java.util.Optional;

public interface ProjectCommandService {
    Optional<Project> handle(CreateProjectCommand command);
    boolean handleDeleteProject(Long id);
}
