package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record CreateProductResource(
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

    public CreateProductResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }
        if (desiredObject == null || desiredObject.isBlank()) {
            throw new IllegalArgumentException("Desired object is required");
        }
        if (price == null) {
            throw new IllegalArgumentException("Price is required");
        }
        if (image == null || image.isBlank()) {
            throw new IllegalArgumentException("Image is required");
        }
        if (boost == null) {
            throw new IllegalArgumentException("Boost is required");
        }
        if (available == null) {
            throw new IllegalArgumentException("Available is required");
        }
        if (productCategoryId == null) {
            throw new IllegalArgumentException("Product category id is required");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User id is required");
        }
        if (districtId == null) {
            throw new IllegalArgumentException("District id is required");
        }
    }
}
