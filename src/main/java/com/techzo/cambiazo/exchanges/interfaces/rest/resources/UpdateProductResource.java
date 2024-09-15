package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record UpdateProductResource(
        String name,
        String description,
        String desiredObject,
        Double price,
        String image,
        Boolean boost,
        Boolean available,
        Long productCategoryId,
        Long userId,
        Long districtId){
}
