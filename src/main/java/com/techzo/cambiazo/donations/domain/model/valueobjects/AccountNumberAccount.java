package com.techzo.cambiazo.donations.domain.model.valueobjects;

public record AccountNumberAccount(String account) {
    public AccountNumberAccount {
        if (account == null || account.isBlank()) {
            throw new IllegalArgumentException("The account is required.");
        }
    }

    public String getAccountNumberAccount() {
        return account;
    }
}
