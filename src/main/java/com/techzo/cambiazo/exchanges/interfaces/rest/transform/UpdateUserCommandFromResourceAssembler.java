package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.UpdateUserCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {

        public static UpdateUserCommand toCommandFromResource(Long userId, UpdateUserResource resource) {
            return new UpdateUserCommand(userId, resource.name(), resource.email(), resource.phoneNumber(), resource.password(), resource.profilePicture());
        }
}
