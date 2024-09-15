package com.techzo.cambiazo.exchanges.domain.model.commands;

public record UpdateSubscriptionCommand(
        Long id,
        String state,
        Long planId,
        Long userId
) {
}
