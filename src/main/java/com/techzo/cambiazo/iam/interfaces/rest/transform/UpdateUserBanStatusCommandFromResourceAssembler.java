package com.techzo.cambiazo.iam.interfaces.rest.transform;

import com.techzo.cambiazo.iam.domain.model.commands.UpdateUserBanStatusCommand;
import com.techzo.cambiazo.iam.interfaces.rest.resources.UpdateUserBanStatusResource;

import java.time.Duration;

public class UpdateUserBanStatusCommandFromResourceAssembler {

    public static UpdateUserBanStatusCommand toCommandFromResource(Long userId, UpdateUserBanStatusResource resource) {
        Duration duration = resource.active() && resource.banDurationMinutes() != null
                ? Duration.ofMinutes(resource.banDurationMinutes())
                : Duration.ZERO;

        return new UpdateUserBanStatusCommand(
                userId,
                resource.active(),
                duration
        );
    }
}