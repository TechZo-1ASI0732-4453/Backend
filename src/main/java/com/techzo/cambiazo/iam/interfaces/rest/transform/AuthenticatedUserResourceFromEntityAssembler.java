package com.techzo.cambiazo.iam.interfaces.rest.transform;

import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), user.getName(), user.getPhoneNumber(), user.getProfilePicture(),user.getIsActive(), token);
    }
}
