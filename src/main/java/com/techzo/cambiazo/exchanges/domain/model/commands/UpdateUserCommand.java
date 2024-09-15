package com.techzo.cambiazo.exchanges.domain.model.commands;

public record UpdateUserCommand(
    Long id,
    String name,
    String email,
    String phoneNumber,
    String password,
    String profilePicture

) {
}
