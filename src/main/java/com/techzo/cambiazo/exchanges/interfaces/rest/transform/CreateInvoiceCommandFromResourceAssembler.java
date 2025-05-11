package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateInvoiceCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateInvoiceResource;

public class CreateInvoiceCommandFromResourceAssembler {
    public static CreateInvoiceCommand toCommandFromResource(CreateInvoiceResource resource) {
        return new CreateInvoiceCommand(
                resource.totalAmount(),
                resource.concept(),
                resource.userId()
        );
    }
}
