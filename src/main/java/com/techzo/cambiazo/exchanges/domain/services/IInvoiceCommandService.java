package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateInvoiceCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Invoice;

import java.util.Optional;

public interface IInvoiceCommandService {
    Optional<Invoice> handle(CreateInvoiceCommand command);
}
