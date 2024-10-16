package com.techzo.cambiazo.donations.domain.model.commands;

public record CreateAccountNumberCommand(String name, String cci, String account, Long ongId) {
}
