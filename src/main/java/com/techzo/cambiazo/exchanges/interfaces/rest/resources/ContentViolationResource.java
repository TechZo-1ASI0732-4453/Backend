package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ContentViolationResource(
        String violationType,
        String message,
        int banDurationMinutes,
        String policyReference,
        Boolean isBanned,
        LocalDateTime bannedUntil
) { }
