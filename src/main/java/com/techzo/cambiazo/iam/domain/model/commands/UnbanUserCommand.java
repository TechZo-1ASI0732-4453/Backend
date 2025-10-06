package com.techzo.cambiazo.iam.domain.model.commands;

/**
 * Command to unban a user
 */
public record UnbanUserCommand(
    Long userId
) {
    public UnbanUserCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
    }
}
