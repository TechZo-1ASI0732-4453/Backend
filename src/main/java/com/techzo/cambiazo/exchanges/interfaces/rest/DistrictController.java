package com.techzo.cambiazo.exchanges.interfaces.rest;

import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllDistrictsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetDistrictByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IDistrictCommandService;
import com.techzo.cambiazo.exchanges.domain.services.IDistrictQueryService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateDistrictResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.DistrictResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateDistrictCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.DistrictResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v2/districts")
@Tag(name="Districts", description="Districts Management Endpoints")
public class DistrictController {

    private final IDistrictCommandService districtCommandService;

    private final IDistrictQueryService districtQueryService;

    public DistrictController(IDistrictCommandService districtCommandService, IDistrictQueryService districtQueryService){
        this.districtCommandService = districtCommandService;
        this.districtQueryService= districtQueryService;
    }

    @Operation(summary="Create a new District", description="Create a new District with the input data.")
    @PostMapping
    public ResponseEntity<DistrictResource> createDistrict(@RequestBody CreateDistrictResource resource){
        try {
            var createDistrictCommand = CreateDistrictCommandFromResourceAssembler.toCommandFromResource(resource);
            var district = districtCommandService.handle(createDistrictCommand);
            var districtResource = DistrictResourceFromEntityAssembler.toResourceFromEntity(district.get());
            return ResponseEntity.status(CREATED).body(districtResource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get a District by ID", description = "Get a District by Id with the params")
    @GetMapping("/{id}")
    public ResponseEntity<DistrictResource>getDistrictById(@PathVariable Long id){
        try{
            var getDistrictByIdQuery=new GetDistrictByIdQuery(id);
            var district = districtQueryService.handle(getDistrictByIdQuery);
            var districtResource= DistrictResourceFromEntityAssembler.toResourceFromEntity(district.get());
            return ResponseEntity.ok(districtResource);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DistrictResource>>getAllDistricts(){
        try{
            var getAllDistrictsQuery = new GetAllDistrictsQuery();
            var districts = districtQueryService.handle(getAllDistrictsQuery);
            var districtResource = districts.stream()
                    .map(DistrictResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(districtResource);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


}
