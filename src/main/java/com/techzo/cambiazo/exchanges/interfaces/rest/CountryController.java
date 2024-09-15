package com.techzo.cambiazo.exchanges.interfaces.rest;

import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllCountriesQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetCountryByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.ICountryCommandService;
import com.techzo.cambiazo.exchanges.domain.services.ICountryQueryService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CountryResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateCountryResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CountryResourceFromEntityAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateCountryCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api/v2/countries")
@Tag(name = "Country", description = "Country Management Endpoints")
public class CountryController {
    private final ICountryCommandService countryCommandService;

    private final ICountryQueryService countryQueryService;

    public CountryController(ICountryCommandService countryCommandService, ICountryQueryService countryQueryService) {
        this.countryCommandService = countryCommandService;
        this.countryQueryService = countryQueryService;
    }

    @Operation(summary = "Create a new Country", description = "Create a new Country with the input data.")
    @PostMapping
    public ResponseEntity<CountryResource>createCountry(@RequestBody CreateCountryResource resource) {
        try{
            var createCountryCommand = CreateCountryCommandFromResourceAssembler.toCommandFromResource(resource);
            var country = countryCommandService.handle(createCountryCommand);
            var countryResource = CountryResourceFromEntityAssembler.toResourceFromEntity(country.get());
            return new ResponseEntity<>(countryResource,CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all Countries", description = "Get all Countries.")
    @GetMapping
    public ResponseEntity<List<CountryResource>>getAllCountries() {
        try {
            var getAllCountriesQuery = new GetAllCountriesQuery();
            var countries = countryQueryService.handle(getAllCountriesQuery);
            var countryResources = countries.stream()
                    .map(CountryResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(countryResources);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get a Country by its ID", description = "Get a Country by its ID.")
    @GetMapping({"/{id}"})
    public ResponseEntity<CountryResource>getCountryById(@PathVariable Long id) {
        try {
            var getCountryByIdQuery = new GetCountryByIdQuery(id);
            var country = countryQueryService.handle(getCountryByIdQuery);
            var countryResource = CountryResourceFromEntityAssembler.toResourceFromEntity(country.get());
            return ResponseEntity.ok(countryResource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
