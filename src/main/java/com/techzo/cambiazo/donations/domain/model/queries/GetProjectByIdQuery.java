package com.techzo.cambiazo.donations.domain.model.queries;

public record GetProjectByIdQuery(Long id) {
    public GetProjectByIdQuery {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
    }

}
