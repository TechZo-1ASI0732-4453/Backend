package com.techzo.cambiazo.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String username, String name, String phoneNumber, String profilePicture, String token) {

}
