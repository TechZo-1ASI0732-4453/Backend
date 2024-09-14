package com.techzo.cambiazo.exchanges.domain.model.commands;

public record CreateCountryCommand(String name) {
    public CreateCountryCommand {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
    }
}
