package com.techzo.cambiazo.donations.domain.services;

import com.techzo.cambiazo.donations.domain.model.aggregates.AccountNumber;
import com.techzo.cambiazo.donations.domain.model.queries.GetAccountNumberByIdQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllAccountNumberByOngIdQuery;

import java.util.List;
import java.util.Optional;

public interface AccountNumberQueryService {
    Optional<AccountNumber>handle(GetAccountNumberByIdQuery query);
    List<AccountNumber>handle(GetAllAccountNumberByOngIdQuery query);
}
