package com.techzo.cambiazo.iam.interfaces.rest.resources;

import java.time.LocalDateTime;

public record AuthenticatedUserResource(
        Long id,
        String username,
        String name,
        String phoneNumber,
        String profilePicture,
        Boolean isActive,
        Boolean isGoogleAccount,
        String token,
        Boolean isBannedFromPosting,
        LocalDateTime bannedUntil
) {
    public AuthenticatedUserResource {
        if (isGoogleAccount == null) {
            isGoogleAccount = false;
        }
    }
}