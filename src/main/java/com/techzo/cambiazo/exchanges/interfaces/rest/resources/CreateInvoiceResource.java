package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CreateInvoiceResource(
        Double totalAmount,
        String concept,
        Long userId
) {}
