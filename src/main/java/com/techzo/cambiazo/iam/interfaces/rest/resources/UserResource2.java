package com.techzo.cambiazo.iam.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record UserResource2(
        Long id,
        String username,
        String name,
        String phoneNumber,
        String profilePicture,
        Date createdAt,
        Boolean isActive,
        Boolean isGoogleAccount,
        List<String> roles,
        Boolean isBannedFromPosting,
        LocalDateTime bannedUntil
) {
    public UserResource2 {
        if (isGoogleAccount == null) {
            isGoogleAccount = false;
        }
    }
}