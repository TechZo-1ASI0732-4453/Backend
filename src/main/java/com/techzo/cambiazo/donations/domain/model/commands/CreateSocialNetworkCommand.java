package com.techzo.cambiazo.donations.domain.model.commands;

public record CreateSocialNetworkCommand(String name, String url, Long ongId) {
    public CreateSocialNetworkCommand{
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("Url cannot be null or empty");
        }
        if (ongId == null) {
            throw new IllegalArgumentException("OngId cannot be null");
        }
    }
}
