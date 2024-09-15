package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record UpdateUserResource(
        String name,
        String email,
        String phoneNumber,
        String password,
        String profilePicture) {
}
