package com.techzo.cambiazo.iam.interfaces.rest.resources;

import java.util.List;

public record SignUpResource(String username, String password,  String name, String phoneNumber, String profilePicture, List<String> roles) {
}
