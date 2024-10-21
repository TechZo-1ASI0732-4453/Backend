package com.techzo.cambiazo.iam.interfaces.rest.transform;

import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.domain.model.entities.Role;
import com.techzo.cambiazo.iam.interfaces.rest.resources.UserResource;
import com.techzo.cambiazo.iam.interfaces.rest.resources.UserResource2;

public class UserResource2FromEntityAssembler {
    public static UserResource2 toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource2(user.getId(), user.getUsername(), user.getName(), user.getPhoneNumber(), user.getProfilePicture(),user.getCreatedAt(), roles);
    }
}
