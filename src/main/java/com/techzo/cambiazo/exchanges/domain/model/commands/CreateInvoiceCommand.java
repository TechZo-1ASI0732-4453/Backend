package com.techzo.cambiazo.exchanges.domain.model.commands;

import java.time.LocalDateTime;

public record CreateInvoiceCommand(
        Double totalAmount,
        String concept,
        Long userId
) {
    public CreateInvoiceCommand {
        if (totalAmount == null || totalAmount <= 0) {
            throw new IllegalArgumentException("TotalAmount must be greater than 0");
        }
        if (concept == null || concept.isBlank()) {
            throw new IllegalArgumentException("Concept is required");
        }
        if (userId == null) {
            throw new IllegalArgumentException("UserId is required");
        }
    }
}
