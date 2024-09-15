package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record CreatePlanResource(String name, String description, Double price) {

    public CreatePlanResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }
        if (price == null) {
            throw new IllegalArgumentException("Price is required");
        }
    }
}
