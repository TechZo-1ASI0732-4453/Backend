package com.techzo.cambiazo.iam.domain.model.commands;

import com.techzo.cambiazo.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(
        String username,
        String password,
        String name,
        String phoneNumber,
        String profilePicture,
        Boolean isGoogleAccount,
        List<Role> roles,
        String recaptchaToken

) {
}
