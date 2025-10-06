package com.techzo.cambiazo.iam.interfaces.rest.resources;

public record UpdateUserBanStatusResource(
        Boolean active,
        Integer banDurationMinutes
) {
    public UpdateUserBanStatusResource {
        if (active == null) {
            throw new IllegalArgumentException("Active status is required");
        }
        if (active && (banDurationMinutes == null || banDurationMinutes <= 0)) {
            throw new IllegalArgumentException("Ban duration in minutes must be greater than 0 when banning a user");
        }
    }
}