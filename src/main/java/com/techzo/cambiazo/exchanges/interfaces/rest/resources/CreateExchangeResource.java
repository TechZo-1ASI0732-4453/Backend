package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record CreateExchangeResource(Long productOwnId, Long productChangeId, String status) {
    public CreateExchangeResource {
        if (productOwnId == null) {
            throw new IllegalArgumentException("Product Own ID is mandatory");
        }
        if (productChangeId == null) {
            throw new IllegalArgumentException("Product Change ID is mandatory");
        }
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status is mandatory");
        }
    }
}
