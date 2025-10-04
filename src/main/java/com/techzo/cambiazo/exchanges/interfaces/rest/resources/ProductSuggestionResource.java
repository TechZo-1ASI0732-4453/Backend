package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record ProductSuggestionResource(
        String name,
        String description,
        String price,
        String category,
        String suggest,
        int score
) {}