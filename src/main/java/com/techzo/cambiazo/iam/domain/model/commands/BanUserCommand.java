package com.techzo.cambiazo.iam.domain.model.commands;

import java.time.Duration;

/**
 * Command to ban a user for a specific duration
 */
public record BanUserCommand(
    Long userId,
    Duration banDuration
) {
    public BanUserCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        if (banDuration == null || banDuration.isNegative() || banDuration.isZero()) {
            throw new IllegalArgumentException("Ban duration must be positive");
        }
    }
}
