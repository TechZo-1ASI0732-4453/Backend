package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.entities.Invoice;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.InvoiceResource;

public class InvoiceResourceFromEntityAssembler {
    public static InvoiceResource toResourceFromEntity(Invoice entity) {
        return new InvoiceResource(
                entity.getId(),
                entity.getAmount(),
                entity.getDescription(),
                entity.getFilePath(),
                entity.getUserId(),
                entity.getIssuedAt()
        );
    }
}
