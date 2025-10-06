package com.techzo.cambiazo.iam.domain.model.commands;

import java.time.Duration;

public record UpdateUserBanStatusCommand(
        Long userId,
        Boolean active,
        Duration banDuration
) {
}