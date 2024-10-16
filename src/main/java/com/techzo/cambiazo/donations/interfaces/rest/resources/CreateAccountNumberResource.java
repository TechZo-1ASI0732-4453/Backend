package com.techzo.cambiazo.donations.interfaces.rest.resources;

public record CreateAccountNumberResource(String name, String cci, String account, Long ongId) {
    public CreateAccountNumberResource {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (cci == null) {
            throw new IllegalArgumentException("cci cannot be null");
        }
        if (account == null) {
            throw new IllegalArgumentException("accountNumber cannot be null");
        }
        if (ongId == null) {
            throw new IllegalArgumentException("ongId cannot be null");
        }
    }
}
