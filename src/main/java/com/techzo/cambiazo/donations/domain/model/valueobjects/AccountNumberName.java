package com.techzo.cambiazo.donations.domain.model.valueobjects;

public record AccountNumberName(String name) {
    public AccountNumberName {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name is required.");
        }
    }

    public String getAccountNumberName(){
        return name;
    }
}
