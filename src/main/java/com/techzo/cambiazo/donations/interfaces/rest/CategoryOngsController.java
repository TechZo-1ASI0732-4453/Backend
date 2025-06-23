package com.techzo.cambiazo.donations.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllCategoryOngsQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetCategoryOngByIdQuery;
import com.techzo.cambiazo.donations.domain.services.CategoryOngCommandService;
import com.techzo.cambiazo.donations.domain.services.CategoryOngQueryService;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CategoryOngResource;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CreateCategoryOngResource;
import com.techzo.cambiazo.donations.interfaces.rest.resources.UpdateCategoryOngResource;
import com.techzo.cambiazo.donations.interfaces.rest.transform.CategoryOngResourceFromEntityAssembler;
import com.techzo.cambiazo.donations.interfaces.rest.transform.CreateCategoryOngCommandFromResourceAssembler;
import com.techzo.cambiazo.donations.interfaces.rest.transform.UpdateCategoryOngCommandFromResourceAssembler;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Controller class for managing CategoryOng operations through REST endpoints.
 *
 * @author CambiaZo - TechZo
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v2/donations/category-ongs")
@Tag(name="CategoryOngs", description="CategoryOngs Management Endpoints")
//@Hidden
public class CategoryOngsController {

    private final CategoryOngCommandService categoryOngCommandService;
    private final CategoryOngQueryService categoryOngQueryService;

    /**
     * Constructor for CategoryOngsController.
     *
     * @param categoryOngCommandService Service handling commands related to CategoryOngs.
     * @param categoryOngQueryService   Service handling queries related to CategoryOngs.
     */
    public CategoryOngsController(CategoryOngCommandService categoryOngCommandService, CategoryOngQueryService categoryOngQueryService){
        this.categoryOngCommandService = categoryOngCommandService;
        this.categoryOngQueryService = categoryOngQueryService;
    }

    /**
     * Endpoint for creating a new CategoryOng.
     *
     * @param resource The resource containing data to create the CategoryOng.
     * @return ResponseEntity containing the created CategoryOngResource.
     */
    @Operation(summary="Create a new CategoryOng", description="Create a new CategoryOng with the input data.")
    @PostMapping
    public ResponseEntity<CategoryOngResource> createCategoryOng(@RequestBody CreateCategoryOngResource resource){
        Optional<CategoryOng> categoryOng = categoryOngCommandService.handle(CreateCategoryOngCommandFromResourceAssembler.toCommandFromResource(resource));
        return categoryOng.map(source -> new ResponseEntity<>(CategoryOngResourceFromEntityAssembler.toResourceFromEntity(source), CREATED)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint for retrieving all CategoryOngs.
     *
     * @return ResponseEntity containing a list of CategoryOngResources.
     */
    @Operation(summary="Get all CategoryOngs", description="Get all CategoryOngs.")
    @GetMapping
    public ResponseEntity<List<CategoryOngResource>> getAllCategoryOngs(){
        var getAllCategoryOngsQuery = new GetAllCategoryOngsQuery();
        var categoryOng = categoryOngQueryService.handle(getAllCategoryOngsQuery);
        var categoryOngResource = categoryOng.stream().map(CategoryOngResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(categoryOngResource);
    }

    /**
     * Endpoint for retrieving a CategoryOng by its ID.
     *
     * @param id The ID of the CategoryOng to retrieve.
     * @return ResponseEntity containing the retrieved CategoryOngResource.
     */
    @Operation(summary="Get CategoryOng by ID", description="Get CategoryOng by ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryOngResource> getCategoryOngById(@PathVariable Long id){
        Optional<CategoryOng> categoryOng = categoryOngQueryService.handle(new GetCategoryOngByIdQuery(id));
        return categoryOng.map(source -> ResponseEntity.ok(CategoryOngResourceFromEntityAssembler.toResourceFromEntity(source))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint for updating a CategoryOng.
     *
     * @param id                    The ID of the CategoryOng to update.
     * @param updateCategoryOngResource The resource containing data to update the CategoryOng.
     * @return ResponseEntity containing the updated CategoryOngResource.
     */
    @Operation(summary="Update a CategoryOng", description="Update a CategoryOng with the input data.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<CategoryOngResource> updateCategoryOng(@PathVariable Long id, @RequestBody UpdateCategoryOngResource updateCategoryOngResource){
        var updateCategoryOngCommand = UpdateCategoryOngCommandFromResourceAssembler.toCommandFromResource(id, updateCategoryOngResource);
        var updateCategoryOng = categoryOngCommandService.handle(updateCategoryOngCommand);
        if (updateCategoryOng.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var categoryOngResource = CategoryOngResourceFromEntityAssembler.toResourceFromEntity(updateCategoryOng.get());
        return ResponseEntity.ok(categoryOngResource);
    }

    /**
     * Endpoint for deleting a CategoryOng by its ID.
     *
     * @param id The ID of the CategoryOng to delete.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @Operation(summary="Delete a CategoryOng", description="Delete a CategoryOng with the input id.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategoryOngById(@PathVariable Long id){
        try {
            categoryOngCommandService.handleDeleteCategoryOng(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
