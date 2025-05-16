package com.techzo.cambiazo.exchanges.interfaces.rest;

import com.techzo.cambiazo.exchanges.domain.services.IInvoiceCommandService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateInvoiceResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.InvoiceResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateInvoiceCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v2/invoices")
@Hidden
@Tag(name = "Invoices", description = "Invoice Management Endpoints")
public class InvoiceController {

    private final IInvoiceCommandService invoiceCommandService;

    public InvoiceController(IInvoiceCommandService invoiceCommandService) {
        this.invoiceCommandService = invoiceCommandService;
    }

    @Operation(summary = "Create a new Invoice", description = "Create a new invoice with the input data.")
    @PostMapping
    public ResponseEntity<InvoiceResource> createInvoice(@RequestBody CreateInvoiceResource resource) {
        try {
            var createInvoiceCommand = CreateInvoiceCommandFromResourceAssembler.toCommandFromResource(resource);
            var invoice = invoiceCommandService.handle(createInvoiceCommand);
            var invoiceResource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(invoice.get());
            return ResponseEntity.status(CREATED).body(invoiceResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
