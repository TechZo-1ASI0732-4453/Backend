package com.techzo.cambiazo.exchanges.domain.model.commands;

public record CreateFavoriteProductCommand(Long productId, Long userId) {

        public CreateFavoriteProductCommand {
            if (userId == null) {
                throw new IllegalArgumentException("User id is required");
            }
            if (productId == null) {
                throw new IllegalArgumentException("Product id is required");
            }
        }
}
