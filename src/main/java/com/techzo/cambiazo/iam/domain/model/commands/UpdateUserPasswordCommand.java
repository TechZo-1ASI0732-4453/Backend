package com.techzo.cambiazo.iam.domain.model.commands;

public record UpdateUserPasswordCommand(String username, String newPassword) {
}
