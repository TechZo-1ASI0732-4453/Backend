package com.techzo.cambiazo.iam.interfaces.rest.transform;

import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.domain.model.entities.Role;
import com.techzo.cambiazo.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPhoneNumber(),
                user.getProfilePicture(),
                user.getIsActive(),
                user.getIsGoogleAccount() != null ? user.getIsGoogleAccount() : false,
                roles,
                user.getBanStatus().isActive(),
                user.getBanStatus().bannedUntil()
        );
    }
}