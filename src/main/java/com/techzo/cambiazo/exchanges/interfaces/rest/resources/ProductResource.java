package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record ProductResource(
        Long id,
        String name,
        String description,
        String desiredObject,
        Double price,
        String image,
        Boolean boost,
        Boolean available,
        Long productCategoryId,
        Long userId,
        Long districtId) {
}
