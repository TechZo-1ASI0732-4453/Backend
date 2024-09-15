package com.techzo.cambiazo.exchanges.domain.model.commands;

public record CreateBenefitCommand(String description, Long planId) {

    public CreateBenefitCommand {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (planId == null) {
            throw new IllegalArgumentException("PlanId cannot be null");
        }
    }
}
