package com.techzo.cambiazo.exchanges.domain.model.commands;

import com.techzo.cambiazo.exchanges.domain.model.ContentViolationType;

/**
 * Command to process content violation and ban user
 */
public record ProcessContentViolationCommand(
    Long userId,
    ContentViolationType violationType,
    String violationReason
) {
    public ProcessContentViolationCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        if (violationType == null) {
            throw new IllegalArgumentException("Violation type cannot be null");
        }
        if (violationType == ContentViolationType.NONE) {
            throw new IllegalArgumentException("Cannot process NONE violation type");
        }
        if (violationReason == null || violationReason.trim().isEmpty()) {
            throw new IllegalArgumentException("Violation reason cannot be empty");
        }
    }
}
