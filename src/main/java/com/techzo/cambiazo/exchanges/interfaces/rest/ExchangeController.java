package com.techzo.cambiazo.exchanges.interfaces.rest;

import com.techzo.cambiazo.exchanges.domain.model.dtos.ModifiedExchange;
import com.techzo.cambiazo.exchanges.domain.model.queries.*;
import com.techzo.cambiazo.exchanges.domain.services.IExchangeCommandService;
import com.techzo.cambiazo.exchanges.domain.services.IExchangeQueryService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateExchangeResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ExchangeResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.UpdateExchangeStatusResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateExchangeCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.ExchangeResourceFromEntityAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.UpdateExchangeStatusCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v2/exchanges")
@Tag(name="Exchanges", description="Exchanges Management Endpoints")
public class ExchangeController {
    private final IExchangeCommandService exchangeCommandService;

    private final IExchangeQueryService exchangeQueryService;

    public ExchangeController(IExchangeCommandService exchangeCommandService, IExchangeQueryService exchangeQueryService) {
        this.exchangeCommandService = exchangeCommandService;
        this.exchangeQueryService = exchangeQueryService;
    }

    @Operation(summary="Create a new Exchange", description="Create a new Exchange with the input data.")
    @PostMapping
    public ResponseEntity<ExchangeResource>createExchange(@RequestBody CreateExchangeResource resource) {
        try {
            var createExchangeCommand = CreateExchangeCommandFromResourceAssembler.toCommandFromResource(resource);
            var exchange = exchangeCommandService.handle(createExchangeCommand);
            var exchangeResource = ExchangeResourceFromEntityAssembler.toResourceFromEntity(exchange.get());
            return ResponseEntity.status(CREATED).body(exchangeResource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{exchangeId}")
    public ResponseEntity<ModifiedExchange>getExchangeById(@PathVariable Long exchangeId) {
        try {
            var getExchangeByIdQuery = new GetExchangeByIdQuery(exchangeId);
            var exchange = exchangeQueryService.handle(getExchangeByIdQuery);
            return ResponseEntity.ok(exchange.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ModifiedExchange>>getAllExchanges() {
        try {
            var getAllExchangesQuery = new GetAllExchangesQuery();
            var exchanges = exchangeQueryService.handle(getAllExchangesQuery);
            return ResponseEntity.ok(exchanges);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/userOwn/{userId}")
    public ResponseEntity<List<ModifiedExchange>>getAllExchangesByUserOwnId(@PathVariable Long userId) {
        try {
            var getAllExchangesByUserOwnIdQuery = new GetAllExchangesByUserOwnIdQuery(userId);
            var exchanges = exchangeQueryService.handle(getAllExchangesByUserOwnIdQuery);
            return ResponseEntity.ok(exchanges);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/userChange/{userId}")
    public ResponseEntity<List<ModifiedExchange>>getAllExchangesByUserChangeId(@PathVariable Long userId) {
        try {
            var getAllExchangesByUserChangeIdQuery = new GetAllExchangesByUserChangeIdQuery(userId);
            var exchanges = exchangeQueryService.handle(getAllExchangesByUserChangeIdQuery);
            return ResponseEntity.ok(exchanges);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    @Operation(summary="Update Exchange Status", description="Update the status of an Exchange.")
    @PutMapping("/status/{exchangeId}")
    public ResponseEntity<ExchangeResource>updateExchangeStatus(@PathVariable Long exchangeId, @RequestBody UpdateExchangeStatusResource resource) {
        try {
            var updateExchangeStatusCommand = UpdateExchangeStatusCommandFromResourceAssembler.toCommandFromResource(exchangeId, resource);
            var exchange = exchangeCommandService.handle(updateExchangeStatusCommand);
            var exchangeResource = ExchangeResourceFromEntityAssembler.toResourceFromEntity(exchange.get());
            return ResponseEntity.ok(exchangeResource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/finished/{userId}")
    public ResponseEntity<List<ModifiedExchange>>getAllFinishedExchangesByUserId(@PathVariable Long userId) {
        try {
            var getAllFinishedExchangesByUserIdQuery = new GetAllFinishedExchangesByUserIdQuery(userId);
            var exchanges = exchangeQueryService.handle(getAllFinishedExchangesByUserIdQuery);
            return ResponseEntity.ok(exchanges);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
