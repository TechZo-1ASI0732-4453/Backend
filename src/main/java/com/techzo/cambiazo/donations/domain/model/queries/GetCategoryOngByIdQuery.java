package com.techzo.cambiazo.donations.domain.model.queries;

public record GetCategoryOngByIdQuery(Long id) {
    public GetCategoryOngByIdQuery {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
    }
}
