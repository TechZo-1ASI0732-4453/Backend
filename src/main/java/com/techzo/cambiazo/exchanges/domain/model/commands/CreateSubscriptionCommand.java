package com.techzo.cambiazo.exchanges.domain.model.commands;

public record CreateSubscriptionCommand(String state, Long planId, Long userId){
    public CreateSubscriptionCommand {
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State is required");
        }
        if (planId == null) {
            throw new IllegalArgumentException("Plan ID is required");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
    }
}
