package com.techzo.cambiazo.iam.interfaces.rest.resources;

public record UpdateUserResource(String username, String password, String name, String phoneNumber, String profilePicture) {
}
