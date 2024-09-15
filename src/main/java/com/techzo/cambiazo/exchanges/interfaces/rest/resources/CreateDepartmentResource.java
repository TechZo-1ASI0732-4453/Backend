package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record CreateDepartmentResource(String name, Long countryId) {
    public CreateDepartmentResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (countryId == null) {
            throw new IllegalArgumentException("CountryId is required");
        }
    }
}
