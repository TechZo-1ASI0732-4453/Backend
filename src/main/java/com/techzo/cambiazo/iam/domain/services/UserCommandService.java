package com.techzo.cambiazo.iam.domain.services;

import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.domain.model.commands.SignInCommand;
import com.techzo.cambiazo.iam.domain.model.commands.SignUpCommand;
import com.techzo.cambiazo.iam.domain.model.commands.UpdateUserCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    Optional<User> handle(SignUpCommand command);
    Optional<User>handle(UpdateUserCommand command);

}
