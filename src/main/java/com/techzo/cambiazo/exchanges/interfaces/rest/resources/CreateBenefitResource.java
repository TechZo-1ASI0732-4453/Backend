package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record CreateBenefitResource(String description, Long planId) {

    public CreateBenefitResource {
        if (description == null) {
            throw new IllegalArgumentException("description cannot be null");
        }
        if (planId == null) {
            throw new IllegalArgumentException("membershipId cannot be null");
        }
    }
}
