package com.techzo.cambiazo.donations.domain.model.commands;

public record CreateCategoryOngCommand(String name) {
    public CreateCategoryOngCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
    }
}
