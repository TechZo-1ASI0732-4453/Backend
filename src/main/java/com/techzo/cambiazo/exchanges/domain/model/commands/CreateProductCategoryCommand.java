package com.techzo.cambiazo.exchanges.domain.model.commands;

public record CreateProductCategoryCommand(String name) {
    public CreateProductCategoryCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
    }
}
