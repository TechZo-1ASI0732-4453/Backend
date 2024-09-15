package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record CreateUserResource(String name, String email, String phoneNumber, String password, String profilePicture) {
    public CreateUserResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (profilePicture == null || profilePicture.isBlank()) {
            throw new IllegalArgumentException("Profile picture is required");
        }
    }
}
