package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record CreateDistrictResource(String name, Long departmentId) {
    public CreateDistrictResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (departmentId == null) {
            throw new IllegalArgumentException("DepartmentId is required");
        }
    }
}
