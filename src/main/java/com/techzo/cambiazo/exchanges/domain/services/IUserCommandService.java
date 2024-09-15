package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateUserCommand;
import com.techzo.cambiazo.exchanges.domain.model.commands.UpdateUserCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.User;

import java.util.Optional;

public interface IUserCommandService {
    Optional<User>handle(CreateUserCommand command);

    Optional<User>handle(UpdateUserCommand command);
    boolean handleDeleteUser(Long id);

}
