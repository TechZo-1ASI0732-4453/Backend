package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record CreateFavoriteProductResource(Long productId, Long userId) {
    public CreateFavoriteProductResource {
        if (productId == null) {
            throw new IllegalArgumentException("Product Id is required");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User Id is required");
        }
    }
}
