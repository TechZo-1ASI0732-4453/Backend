package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record ContentViolationResource(
        String violationType,
        String message,
        int banDurationMinutes,
        String policyReference
) { }
