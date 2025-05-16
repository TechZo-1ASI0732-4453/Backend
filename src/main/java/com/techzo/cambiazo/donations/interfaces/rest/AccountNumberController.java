package com.techzo.cambiazo.donations.interfaces.rest;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.techzo.cambiazo.donations.domain.model.queries.GetAccountNumberByIdQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllAccountNumberByOngIdQuery;
import com.techzo.cambiazo.donations.domain.services.AccountNumberCommandService;
import com.techzo.cambiazo.donations.domain.services.AccountNumberQueryService;
import com.techzo.cambiazo.donations.interfaces.rest.resources.AccountNumberResource;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CreateAccountNumberResource;
import com.techzo.cambiazo.donations.interfaces.rest.transform.AccountNumberResourceFromEntityAssembler;
import com.techzo.cambiazo.donations.interfaces.rest.transform.CreateAccountNumberCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Controller class for managing Account Number operations through REST endpoints.
 *
 * @author  CambiaZo - TechZo
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v2/donations/account-number")
@Tag(name="Account Number", description="Account Number Management Endpoints")
@Hidden
public class AccountNumberController {

    private final AccountNumberCommandService accountNumberCommandService;
    private final AccountNumberQueryService accountNumberQueryService;

    /**
     * Constructor for AccountNumberController.
     *
     * @param accountNumberCommandService Service handling commands related to Account Numbers.
     * @param accountNumberQueryService   Service handling queries related to Account Numbers.
     */
    public AccountNumberController(AccountNumberCommandService accountNumberCommandService, AccountNumberQueryService accountNumberQueryService){
        this.accountNumberCommandService = accountNumberCommandService;
        this.accountNumberQueryService = accountNumberQueryService;
    }

    /**
     * Endpoint for creating a new Account Number.
     *
     * @param resource The resource containing data to create the Account Number.
     * @return ResponseEntity containing the created AccountNumberResource.
     */
    @Operation(summary="Create a new Account Number", description="Create a new Account Number with the input data.")
    @PostMapping
    public ResponseEntity<AccountNumberResource> createAccountNumber(@RequestBody CreateAccountNumberResource resource){
        try {
            var createAccountNumberCommand = CreateAccountNumberCommandFromResourceAssembler.toCommandFromResource(resource);
            var accountNumber = accountNumberCommandService.handle(createAccountNumberCommand);
            var accountNumberResource = AccountNumberResourceFromEntityAssembler.toResourceFromEntity(accountNumber.get());
            return ResponseEntity.status(CREATED).body(accountNumberResource);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint for retrieving an Account Number by its ID.
     *
     * @param id The ID of the Account Number to retrieve.
     * @return ResponseEntity containing the retrieved AccountNumberResource.
     */
    @Operation(summary="Get Account Number by Id", description="Get Account Number by Id.")
    @GetMapping("/{id}")
    public ResponseEntity<AccountNumberResource> getAccountNumberById(@PathVariable Long id){
        try {
            var getAccountNumberByIdQuery = new GetAccountNumberByIdQuery(id);
            var accountNumber = accountNumberQueryService.handle(getAccountNumberByIdQuery);
            var accountNumberResource = AccountNumberResourceFromEntityAssembler.toResourceFromEntity(accountNumber.get());
            return ResponseEntity.ok(accountNumberResource);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint for retrieving all Account Numbers associated with an Ong ID.
     *
     * @param ongId The ID of the Ong to retrieve Account Numbers for.
     * @return ResponseEntity containing a list of AccountNumberResources.
     */
    @Operation(summary="Get All Account Number by Ong Id", description="Get All Account Number by Ong Id.")
    @GetMapping("/ongs/{ongId}")
    public ResponseEntity<List<AccountNumberResource>> getAllAccountNumberByOngId(@PathVariable Long ongId){
        try {
            var getAllAccountNumberByOngIdQuery = new GetAllAccountNumberByOngIdQuery(ongId);
            var accountNumbers = accountNumberQueryService.handle(getAllAccountNumberByOngIdQuery);
            var accountNumberResources = accountNumbers.stream()
                    .map(AccountNumberResourceFromEntityAssembler::toResourceFromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(accountNumberResources);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint for deleting an Account Number by its ID.
     *
     * @param id The ID of the Account Number to delete.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @Operation(summary="Delete Account Number", description="Delete Account Number by Id.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAccountNumber(@PathVariable Long id){
        try {
            accountNumberCommandService.handleDeleteAccountNumber(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

}
