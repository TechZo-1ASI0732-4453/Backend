package com.techzo.cambiazo.iam.interfaces.rest.transform;

import com.techzo.cambiazo.iam.domain.model.commands.UpdateUserCommand;
import com.techzo.cambiazo.iam.interfaces.rest.resources.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {
    public static UpdateUserCommand toCommandFromResource(Long userId,UpdateUserResource resource) {
        return new UpdateUserCommand(userId,resource.username(), resource.password(), resource.name(), resource.phoneNumber(), resource.profilePicture());
    }
}
