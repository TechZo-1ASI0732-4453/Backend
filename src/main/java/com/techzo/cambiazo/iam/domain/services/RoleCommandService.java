package com.techzo.cambiazo.iam.domain.services;

import com.techzo.cambiazo.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
