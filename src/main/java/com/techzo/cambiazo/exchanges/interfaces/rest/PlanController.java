package com.techzo.cambiazo.exchanges.interfaces.rest;


import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllPlansQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetPlanByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IPlanCommandService;
import com.techzo.cambiazo.exchanges.domain.services.IPlanQueryService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreatePlanResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.PlanResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreatePlanCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.PlanResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v2/plans")
@Tag(name = "Plans", description = "Plans Management Endpoints")
public class PlanController {
    private final IPlanCommandService planCommandService;

    private final IPlanQueryService planQueryService;

    public PlanController(IPlanCommandService planCommandService, IPlanQueryService planQueryService) {
        this.planCommandService = planCommandService;
        this.planQueryService=planQueryService;
    }

    @Operation(summary = "Create a new Plan", description = "Create a new Plan with the input data.")
    @PostMapping
    public ResponseEntity<PlanResource>createPlan(@RequestBody CreatePlanResource resource){
        try{
            var createPlanCommand= CreatePlanCommandFromResourceAssembler.toCommandFromResource(resource);
            var plan= planCommandService.handle(createPlanCommand);
            var planResource= PlanResourceFromEntityAssembler.toResourceFromEntity(plan.get());
            return ResponseEntity.status(CREATED).body(planResource);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResource>getPlanById(@PathVariable Long id){
        try{
            var getPlanByIdQuery = new GetPlanByIdQuery(id);
            var plan = planQueryService.handle(getPlanByIdQuery);
            var planResource = PlanResourceFromEntityAssembler.toResourceFromEntity(plan.get());
            return ResponseEntity.ok(planResource);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PlanResource>>getAllPlans(){
        try {
            var getAllPlanQuery = new GetAllPlansQuery();
            var plans = planQueryService.handle(getAllPlanQuery);
            var planResource = plans.stream()
                    .map(PlanResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(planResource);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
