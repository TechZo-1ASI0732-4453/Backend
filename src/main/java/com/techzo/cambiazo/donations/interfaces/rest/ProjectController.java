package com.techzo.cambiazo.donations.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllProjectsQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetProjectByIdQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetProjectsByOngIdQuery;
import com.techzo.cambiazo.donations.domain.services.ProjectCommandService;
import com.techzo.cambiazo.donations.domain.services.ProjectQueryService;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CreateProjectResource;
import com.techzo.cambiazo.donations.interfaces.rest.resources.ProjectResource;
import com.techzo.cambiazo.donations.interfaces.rest.transform.CreateProjectCommandFromResourceAssembler;
import com.techzo.cambiazo.donations.interfaces.rest.transform.ProjectResourceFromEntityAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Controller class for managing Project operations through REST endpoints.
 *
 * @author CambiaZo
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v2/donations/projects")
@Tag(name="Projects", description="Projects Management Endpoints")
@Hidden
public class ProjectController {

    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    /**
     * Constructor for ProjectController.
     *
     * @param projectCommandService Service handling commands related to Projects.
     * @param projectQueryService   Service handling queries related to Projects.
     */
    public ProjectController(ProjectCommandService projectCommandService, ProjectQueryService projectQueryService){
        this.projectCommandService = projectCommandService;
        this.projectQueryService = projectQueryService;
    }

    /**
     * Endpoint to create a new Project.
     *
     * @param resource The resource containing data to create the Project.
     * @return ResponseEntity containing the created ProjectResource.
     */
    @Operation(summary="Create a new Project", description="Create a new Project with the input data.")
    @PostMapping
    public ResponseEntity<ProjectResource> createProject(@RequestBody CreateProjectResource resource){
        try {
            var createProjectCommand = CreateProjectCommandFromResourceAssembler.toCommandFromResource(resource);
            var project = projectCommandService.handle(createProjectCommand);
            var projectResource = ProjectResourceFromEntityAssembler.toResourceFromEntity(project.get());
            return ResponseEntity.status(CREATED).body(projectResource);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to get all Projects.
     *
     * @return ResponseEntity containing a list of ProjectResources.
     */
    @Operation(summary="Get all Projects", description="Get all Projects.")
    @GetMapping
    public ResponseEntity<List<ProjectResource>> getAllProjects(){
        try {
            var getAllProjectsQuery = new GetAllProjectsQuery();
            var projects = projectQueryService.handle(getAllProjectsQuery);
            var projectResources = projects.stream().map(ProjectResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
            return ResponseEntity.ok(projectResources);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to get a Project by its ID.
     *
     * @param id The ID of the Project to retrieve.
     * @return ResponseEntity containing the retrieved ProjectResource.
     */
    @Operation(summary="Get Project by ID", description="Get Project by ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResource> getProjectById(@PathVariable Long id){
        try {
            var getProjectByIdQuery = new GetProjectByIdQuery(id);
            var project = projectQueryService.handle(getProjectByIdQuery);
            var projectResource = ProjectResourceFromEntityAssembler.toResourceFromEntity(project.get());
            return ResponseEntity.ok(projectResource);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to get all Projects associated with an Ong ID.
     *
     * @param ongId The ID of the Ong to retrieve Projects for.
     * @return ResponseEntity containing a list of ProjectResources.
     */
    @Operation(summary="Get Projects by Ong ID", description="Get Projects by Ong ID.")
    @GetMapping("/ongs/{ongId}")
    public ResponseEntity<List<ProjectResource>> getProjectsByOngId(@PathVariable Long ongId){
        try {
            var getProjectsByOngIdQuery = new GetProjectsByOngIdQuery(ongId);
            var projects = projectQueryService.handle(getProjectsByOngIdQuery);
            var projectResources = projects.stream().map(ProjectResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
            return ResponseEntity.ok(projectResources);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to delete a Project by its ID.
     *
     * @param id The ID of the Project to delete.
     * @return ResponseEntity indicating success or failure of the deletion.
     */
    @Operation(summary="Delete Project by ID", description="Delete Project by ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        try {
            projectCommandService.handleDeleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

}
