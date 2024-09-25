package com.techzo.cambiazo.donations.domain.services;

import com.techzo.cambiazo.donations.domain.model.aggregates.Project;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllProjectsQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetProjectByIdQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetProjectsByOngIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryService {

    List<Project>handle(GetProjectsByOngIdQuery query);
    List<Project>handle(GetAllProjectsQuery query);
    Optional<Project>handle(GetProjectByIdQuery query);
}
