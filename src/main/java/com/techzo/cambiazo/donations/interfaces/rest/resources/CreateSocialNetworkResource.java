package com.techzo.cambiazo.donations.interfaces.rest.resources;

public record CreateSocialNetworkResource(String name, String url, Long ongId) {
    public CreateSocialNetworkResource {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (url == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        if (ongId == null) {
            throw new IllegalArgumentException("ongId cannot be null");
        }
    }
}
