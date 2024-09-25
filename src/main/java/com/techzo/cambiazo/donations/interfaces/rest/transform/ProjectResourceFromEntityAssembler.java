package com.techzo.cambiazo.donations.interfaces.rest.transform;


import com.techzo.cambiazo.donations.domain.model.aggregates.Project;
import com.techzo.cambiazo.donations.interfaces.rest.resources.ProjectResource;

public class ProjectResourceFromEntityAssembler {
    public static ProjectResource toResourceFromEntity(Project entity) {
        return new ProjectResource(entity.getId(), entity.getName(), entity.getDescription(), entity.getOngId());
    }
}
