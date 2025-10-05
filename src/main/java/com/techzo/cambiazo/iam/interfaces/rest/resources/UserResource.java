package com.techzo.cambiazo.iam.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.List;

public record UserResource(
        Long id,
        String username,
        String name,
        String phoneNumber,
        String profilePicture,
        Boolean isActive,
        Boolean isGoogleAccount,
        List<String> roles,
        Boolean isBannedFromPosting,
        LocalDateTime bannedUntil
) {
    public UserResource {
        if (isGoogleAccount == null) {
            isGoogleAccount = false;
        }
    }
}
