package com.techzo.cambiazo.iam.interfaces.rest.resources;

import java.util.Date;
import java.util.List;

public record UserResource2(Long id, String username, String name, String phoneNumber, String profilePicture, Date createdAt, Boolean isActive, List<String> roles) {
}
