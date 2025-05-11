package com.techzo.cambiazo.exchanges.interfaces.rest;

import com.techzo.cambiazo.exchanges.domain.services.IInvoiceCommandService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateInvoiceResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateInvoiceCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v2/invoices")
@Tag(name = "Invoices", description = "Invoice Management Endpoints")
public class InvoiceController {

    private final IInvoiceCommandService invoiceCommandService;

    public InvoiceController(IInvoiceCommandService invoiceCommandService) {
        this.invoiceCommandService = invoiceCommandService;
    }

    @Operation(summary = "Create a new Invoice", description = "Create a new invoice with the input data.")
    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody CreateInvoiceResource resource) {
        try {
            var cmd = CreateInvoiceCommandFromResourceAssembler.toCommandFromResource(resource);
            var invoiceOpt = invoiceCommandService.handle(cmd);
            var invoiceRes = InvoiceResourceFromEntityAssembler
                    .toResourceFromEntity(invoiceOpt.orElseThrow());
            return ResponseEntity.status(CREATED).body(invoiceRes);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Datos inválidos", e.getMessage()));

        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("No autenticado", e.getMessage()));

        } catch (AccessDeniedException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Acceso denegado", e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno del servidor", e.getMessage()));
        }
    }

    private record ErrorResponse(String error, String message) {}
}
