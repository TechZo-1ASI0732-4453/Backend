package com.techzo.cambiazo.exchanges.domain.model.commands;

public record UpdateProductCommand(
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
        Long districtId){
}
