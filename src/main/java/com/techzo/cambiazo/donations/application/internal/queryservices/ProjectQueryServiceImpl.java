package com.techzo.cambiazo.donations.application.internal.queryservices;

import org.springframework.stereotype.Service;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.aggregates.Project;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllProjectsQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetProjectByIdQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetProjectsByOngIdQuery;
import com.techzo.cambiazo.donations.domain.services.ProjectQueryService;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.OngRepository;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.ProjectRepository;

import java.util.List;
import java.util.Optional;


@Service
public class ProjectQueryServiceImpl implements ProjectQueryService {

    private final ProjectRepository projectRepository;

    private final OngRepository ongRepository;

    public ProjectQueryServiceImpl(ProjectRepository projectRepository, OngRepository ongRepository) {
        this.projectRepository = projectRepository;
        this.ongRepository = ongRepository;
    }

    @Override
    public List<Project> handle(GetAllProjectsQuery query) {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> handle(GetProjectByIdQuery query) {
        return projectRepository.findById(query.id());
    }

    @Override
    public List<Project>handle(GetProjectsByOngIdQuery query) {
        Ong ong = ongRepository.findById(query.ongId())
                .orElseThrow(() -> new IllegalArgumentException("Ong with id " + query.ongId() + " not found"));
        return projectRepository.findByOngId(ong);
    }

}

