package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record ErrorResource(
        String error,
        String message,
        Long remainingBanMinutes,
        String reason
) {}
