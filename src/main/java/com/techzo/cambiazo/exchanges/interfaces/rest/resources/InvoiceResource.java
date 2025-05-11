package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

import java.time.LocalDateTime;

public record InvoiceResource(
        Long id,
        Double amount,
        String description,
        String filePath,
        Long userId,
        LocalDateTime issuedAt
) {
}
