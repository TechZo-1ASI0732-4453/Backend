package com.techzo.cambiazo.donations.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.techzo.cambiazo.donations.domain.model.queries.*;
import com.techzo.cambiazo.donations.domain.services.OngCommandService;
import com.techzo.cambiazo.donations.domain.services.OngQueryService;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CreateOngResource;
import com.techzo.cambiazo.donations.interfaces.rest.resources.OngResource;
import com.techzo.cambiazo.donations.interfaces.rest.resources.UpdateOngResource;
import com.techzo.cambiazo.donations.interfaces.rest.transform.CreateOngCommandFromResourceAssembler;
import com.techzo.cambiazo.donations.interfaces.rest.transform.OngResourceFromEntityAssembler;
import com.techzo.cambiazo.donations.interfaces.rest.transform.UpdateOngCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for managing ONG operations through REST endpoints.
 *
 * @author CambiaZo
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/ongs", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Ongs", description = "Ongs Management Endpoints")
public class OngsController {

    private final OngCommandService ongCommandService;
    private final OngQueryService ongQueryService;

    /**
     * Constructor for OngsController.
     *
     * @param ongCommandService Service handling commands related to ONGs.
     * @param ongQueryService   Service handling queries related to ONGs.
     */
    public OngsController(OngCommandService ongCommandService, OngQueryService ongQueryService) {
        this.ongCommandService = ongCommandService;
        this.ongQueryService = ongQueryService;
    }

    /**
     * Endpoint to create a new ONG.
     *
     * @param resource The resource containing data to create the ONG.
     * @return ResponseEntity containing the created OngResource.
     */
    @Operation(summary = "Create a new Ong", description = "Create a new Ong with the input data.")
    @PostMapping
    public ResponseEntity<OngResource> createOng(@RequestBody CreateOngResource resource) {
        try {
            var createOngCommand = CreateOngCommandFromResourceAssembler.toCommandFromResource(resource);
            var ong = ongCommandService.handle(createOngCommand);
            var ongResource = OngResourceFromEntityAssembler.toResourceFromEntity(ong.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(ongResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to get all ONGs.
     *
     * @return ResponseEntity containing a list of OngResources.
     */
    @Operation(summary = "Get all Ongs", description = "Get all Ongs.")
    @GetMapping
    public ResponseEntity<List<OngResource>> getAllOngs() {
        try {
            var getAllOngsQuery = new GetAllOngsQuery();
            var ongs = ongQueryService.handle(getAllOngsQuery);
            var ongResources = ongs.stream().map(OngResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
            return ResponseEntity.ok(ongResources);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to get an ONG by its ID.
     *
     * @param id The ID of the ONG to retrieve.
     * @return ResponseEntity containing the retrieved OngResource.
     */
    @Operation(summary = "Get Ong by ID", description = "Get Ong by ID.")
    @GetMapping("/{id}")
    public ResponseEntity<OngResource> getOngById(@PathVariable Long id) {
        try {
            var getOngByIdQuery = new GetOngByIdQuery(id);
            var ong = ongQueryService.handle(getOngByIdQuery);
            var ongResource = OngResourceFromEntityAssembler.toResourceFromEntity(ong.get());
            return ResponseEntity.ok(ongResource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to get all ONGs by a Category ID.
     *
     * @param categoryId The ID of the Category to filter ONGs.
     * @return ResponseEntity containing a list of OngResources filtered by Category.
     */
    @Operation(summary = "Get Ongs by Category", description = "Get Ongs by Category.")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<OngResource>> getOngsByCategory(@PathVariable Long categoryId) {
        try {
            var getOngsByCategoryQuery = new GetOngsByCategoryOngIdQuery(categoryId);
            var ongs = ongQueryService.handle(getOngsByCategoryQuery);
            var ongResources = ongs.stream().map(OngResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
            return ResponseEntity.ok(ongResources);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to search ONGs by letters in their name.
     *
     * @param letters The letters to search for in ONG names.
     * @return ResponseEntity containing a list of OngResources matching the search criteria.
     */
    @Operation(summary = "Get Ongs by Letters", description = "Get Ongs by Letters.")
    @GetMapping("/search/{letters}")
    public ResponseEntity<List<OngResource>> getOngsByLetters(@PathVariable String letters) {
        try {
            var getOngByLettersQuery = new GetOngByLettersQuery(letters);
            var ongs = ongQueryService.handle(getOngByLettersQuery);
            var ongResources = ongs.stream().map(OngResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
            return ResponseEntity.ok(ongResources);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to update an existing ONG.
     *
     * @param ongId              The ID of the ONG to update.
     * @param updateOngResource  The resource containing data to update the ONG.
     * @return ResponseEntity containing the updated OngResource.
     */
    @Operation(summary = "Update Ong", description = "Update Ong with the input data.")
    @PutMapping("/edit/{ongId}")
    public ResponseEntity<OngResource> updateOng(@PathVariable Long ongId, @RequestBody UpdateOngResource updateOngResource) {
        var updateOngCommand = UpdateOngCommandFromResourceAssembler.toCommandFromResource(ongId, updateOngResource);
        var updatedOng = ongCommandService.handle(updateOngCommand);
        if (updatedOng.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var ongResource = OngResourceFromEntityAssembler.toResourceFromEntity(updatedOng.get());
        return ResponseEntity.ok(ongResource);
    }

    /**
     * Endpoint to delete an existing ONG by its ID.
     *
     * @param id The ID of the ONG to delete.
     * @return ResponseEntity indicating success or failure of the deletion.
     */
    @Operation(summary = "Delete Ong by ID", description = "Delete Ong by ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOng(@PathVariable Long id) {
        try {
            ongCommandService.handleDeleteOng(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
