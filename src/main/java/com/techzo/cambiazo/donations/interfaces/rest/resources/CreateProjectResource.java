package com.techzo.cambiazo.donations.interfaces.rest.resources;

public record CreateProjectResource(String name, String description, Long ongId) {
    public CreateProjectResource {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("description cannot be null");
        }
        if (ongId == null) {
            throw new IllegalArgumentException("ongId cannot be null");
        }
    }
}
