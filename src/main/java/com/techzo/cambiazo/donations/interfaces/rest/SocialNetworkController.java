package com.techzo.cambiazo.donations.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllSocialNetworkByOngIdQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllSocialNetworksQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetSocialNetworkByIdQuery;
import com.techzo.cambiazo.donations.domain.services.SocialNetworkCommandService;
import com.techzo.cambiazo.donations.domain.services.SocialNetworkQueryService;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CreateSocialNetworkResource;
import com.techzo.cambiazo.donations.interfaces.rest.resources.SocialNetworkResource;
import com.techzo.cambiazo.donations.interfaces.rest.transform.CreateSocialNetworkCommandFromResourceAssembler;
import com.techzo.cambiazo.donations.interfaces.rest.transform.SocialNetworkResourceFromEntityAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Controller class for managing Social Network operations through REST endpoints.
 *
 * @author CambiaZo
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v2/donations/social-networks")
@Tag(name="Social Networks", description="Social Networks Management Endpoints")
//@Hidden
public class SocialNetworkController {

    private final SocialNetworkCommandService socialNetworkCommandService;
    private final SocialNetworkQueryService socialNetworkQueryService;

    /**
     * Constructor for SocialNetworkController.
     *
     * @param socialNetworkCommandService Service handling commands related to Social Networks.
     * @param socialNetworkQueryService   Service handling queries related to Social Networks.
     */
    public SocialNetworkController(SocialNetworkCommandService socialNetworkCommandService, SocialNetworkQueryService socialNetworkQueryService){
        this.socialNetworkCommandService = socialNetworkCommandService;
        this.socialNetworkQueryService = socialNetworkQueryService;
    }

    /**
     * Endpoint to create a new Social Network.
     *
     * @param resource The resource containing data to create the Social Network.
     * @return ResponseEntity containing the created SocialNetworkResource.
     */
    @Operation(summary="Create a new Social Network", description="Create a new Social Network with the input data.")
    @PostMapping
    public ResponseEntity<SocialNetworkResource> createSocialNetwork(@RequestBody CreateSocialNetworkResource resource){
        try {
            var createSocialNetworkCommand = CreateSocialNetworkCommandFromResourceAssembler.toCommandFromResource(resource);
            var socialNetwork = socialNetworkCommandService.handle(createSocialNetworkCommand);
            var socialNetworkResource = SocialNetworkResourceFromEntityAssembler.toResourceFromEntity(socialNetwork.get());
            return ResponseEntity.status(CREATED).body(socialNetworkResource);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to get all Social Networks.
     *
     * @return ResponseEntity containing a list of SocialNetworkResources.
     */
    @Operation(summary="Get all Social Networks", description="Get all Social Networks.")
    @GetMapping
    public ResponseEntity<List<SocialNetworkResource>> getAllSocialNetworks(){
        try {
            var getAllSocialNetworksQuery = new GetAllSocialNetworksQuery();
            var socialNetworks = socialNetworkQueryService.handle(getAllSocialNetworksQuery);
            var socialNetworkResources = socialNetworks.stream().map(SocialNetworkResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
            return ResponseEntity.ok(socialNetworkResources);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to get a Social Network by its ID.
     *
     * @param id The ID of the Social Network to retrieve.
     * @return ResponseEntity containing the retrieved SocialNetworkResource.
     */
    @Operation(summary="Get a Social Network by Id", description="Get a Social Network by Id.")
    @GetMapping("/{id}")
    public ResponseEntity<SocialNetworkResource> getSocialNetworkById(@PathVariable Long id){
        try {
            var getSocialNetworkByIdQuery = new GetSocialNetworkByIdQuery(id);
            var socialNetwork = socialNetworkQueryService.handle(getSocialNetworkByIdQuery);
            var socialNetworkResource = SocialNetworkResourceFromEntityAssembler.toResourceFromEntity(socialNetwork.get());
            return ResponseEntity.ok(socialNetworkResource);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to get all Social Networks associated with an Ong ID.
     *
     * @param id The ID of the Ong to retrieve Social Networks for.
     * @return ResponseEntity containing a list of SocialNetworkResources.
     */
    @Operation(summary="Get Social Networks by Ong Id", description="Get Social Networks by Ong Id.")
    @GetMapping("/ongs/{id}")
    public ResponseEntity<List<SocialNetworkResource>> getSocialNetworksByOngId(@PathVariable Long id){
        try {
            var getSocialNetworksByOngIdQuery = new GetAllSocialNetworkByOngIdQuery(id);
            var socialNetworks = socialNetworkQueryService.handle(getSocialNetworksByOngIdQuery);
            var socialNetworkResources = socialNetworks.stream().map(SocialNetworkResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
            return ResponseEntity.ok(socialNetworkResources);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to delete a Social Network by its ID.
     *
     * @param id The ID of the Social Network to delete.
     * @return ResponseEntity indicating success or failure of the deletion.
     */
    @Operation(summary="Delete a Social Network by Id", description="Delete a Social Network by Id.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSocialNetwork(@PathVariable Long id){
        try {
            socialNetworkCommandService.handleDeleteSocialNetwork(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

}
