package com.techzo.cambiazo.iam.interfaces.rest.transform;

import com.techzo.cambiazo.iam.domain.model.commands.SignUpCommand;
import com.techzo.cambiazo.iam.domain.model.entities.Role;
import com.techzo.cambiazo.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = resource.roles() != null ? resource.roles().stream().map(name -> Role.toRoleFromName(name)).toList() : new ArrayList<Role>();
        return new SignUpCommand(resource.username(), resource.password(), resource.name(), resource.phoneNumber(), resource.profilePicture(), roles);
    }
}
