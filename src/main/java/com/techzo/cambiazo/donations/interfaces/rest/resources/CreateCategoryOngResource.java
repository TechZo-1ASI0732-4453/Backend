package com.techzo.cambiazo.donations.interfaces.rest.resources;

public record CreateCategoryOngResource(String name) {
    public CreateCategoryOngResource {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
    }
}
