package com.techzo.cambiazo.iam.interfaces.rest.transform;

import com.techzo.cambiazo.iam.domain.model.commands.UpdateProfileUserCommand;
import com.techzo.cambiazo.iam.interfaces.rest.resources.UpdateUserProfileResource;

public class UpdateProfileUserCommandFromResourceAssembler {
    public static UpdateProfileUserCommand toCommandFromResource(Long userId, UpdateUserProfileResource resource) {
        return new UpdateProfileUserCommand(userId,resource.username(), resource.name(), resource.phoneNumber(), resource.profilePicture());
    }
}
