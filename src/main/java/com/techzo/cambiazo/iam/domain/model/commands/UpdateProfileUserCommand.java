package com.techzo.cambiazo.iam.domain.model.commands;

public record UpdateProfileUserCommand(Long userId, String name, String phoneNumber, String profilePicture) {
}
