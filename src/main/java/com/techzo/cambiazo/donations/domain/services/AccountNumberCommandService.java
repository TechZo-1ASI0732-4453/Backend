package com.techzo.cambiazo.donations.domain.services;

import com.techzo.cambiazo.donations.domain.model.aggregates.AccountNumber;
import com.techzo.cambiazo.donations.domain.model.commands.CreateAccountNumberCommand;

import java.util.Optional;

public interface AccountNumberCommandService {

    Optional<AccountNumber>handle(CreateAccountNumberCommand command);
    boolean handleDeleteAccountNumber(Long id);
}
