package com.techzo.cambiazo.donations.domain.model.commands;

public record CreateProjectCommand(String name, String description, Long ongId) {
    public CreateProjectCommand{
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (ongId == null) {
            throw new IllegalArgumentException("OngId cannot be null");
        }
    }
}
