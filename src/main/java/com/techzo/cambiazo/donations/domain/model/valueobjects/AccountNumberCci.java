package com.techzo.cambiazo.donations.domain.model.valueobjects;

public record AccountNumberCci(String cci) {
    public AccountNumberCci {
        if (cci == null || cci.isBlank()) {
            throw new IllegalArgumentException("The cci is required.");
        }
    }

    public String getAccountNumberCci(){
        return cci;
    }
}
