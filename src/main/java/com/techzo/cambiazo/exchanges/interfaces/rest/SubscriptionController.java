package com.techzo.cambiazo.exchanges.interfaces.rest;


import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllSubscriptionsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetSubscriptionByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.ISubscriptionCommandService;
import com.techzo.cambiazo.exchanges.domain.services.ISubscriptionQueryService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateSubscriptionResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.SubscriptionResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.UpdateSubscriptionResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.UpdateSubscriptionCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v2/subscriptions")
@Tag(name = "Subscriptions", description = "Subscriptions Management Endpoints")
public class SubscriptionController {

    private final ISubscriptionCommandService subscriptionCommandService;

    private final ISubscriptionQueryService subscriptionQueryService;

    public SubscriptionController(ISubscriptionCommandService subscriptionCommandService, ISubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }


    @Operation(summary = "Create a new Subscription", description = "Create a new Subscription with the input data.")
    @PostMapping
    public ResponseEntity<SubscriptionResource>createSubscription(@RequestBody CreateSubscriptionResource resource){
        try{
            var createSubscriptionCommand = CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource);
            var subscription = subscriptionCommandService.handle(createSubscriptionCommand);
            var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
            return ResponseEntity.status(CREATED).body(subscriptionResource);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResource>getSubscriptionById(@PathVariable Long id){
        try{
            var getSubscriptionByIdQuery = new GetSubscriptionByIdQuery(id);
            var subscription = subscriptionQueryService.handle(getSubscriptionByIdQuery);
            var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
            return ResponseEntity.ok(subscriptionResource);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<SubscriptionResource>getSubscriptionByUserId(@PathVariable Long id){
        try{
            var getSubscriptionByUserIdQuery = new GetSubscriptionByIdQuery(id);
            var subscription = subscriptionQueryService.handle(getSubscriptionByUserIdQuery);
            var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
            return ResponseEntity.ok(subscriptionResource);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<SubscriptionResource>>getAllSubscriptions(){
        try {
            var getAllSubscriptionQuery = new GetAllSubscriptionsQuery();
            var subscriptions = subscriptionQueryService.handle(getAllSubscriptionQuery);
            var subscriptionResource = subscriptions.stream()
                    .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(subscriptionResource);
        }catch (Exception e ){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update a Subscription", description = "Update a Subscription with the input data.")
    @PutMapping("/status/{subscriptionId}")
    public ResponseEntity<SubscriptionResource>updateSubscriptionStatus(@PathVariable Long subscriptionId, @RequestBody UpdateSubscriptionResource resource){
        try{
            var updateSubscriptionStatusCommand = UpdateSubscriptionCommandFromResourceAssembler.toCommandFromResource(subscriptionId, resource);
            var subscription = subscriptionCommandService.handle(updateSubscriptionStatusCommand);
            var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
            return ResponseEntity.ok(subscriptionResource);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
