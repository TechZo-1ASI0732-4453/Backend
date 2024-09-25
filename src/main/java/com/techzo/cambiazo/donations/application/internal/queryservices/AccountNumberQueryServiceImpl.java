package com.techzo.cambiazo.donations.application.internal.queryservices;


import org.springframework.stereotype.Service;
import com.techzo.cambiazo.donations.domain.model.aggregates.AccountNumber;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.queries.GetAccountNumberByIdQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllAccountNumberByOngIdQuery;
import com.techzo.cambiazo.donations.domain.services.AccountNumberQueryService;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.AccountNumberRepository;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.OngRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountNumberQueryServiceImpl implements AccountNumberQueryService {

    private final AccountNumberRepository accountNumberRepository;

    private final OngRepository ongRepository;

    public AccountNumberQueryServiceImpl(AccountNumberRepository accountNumberRepository, OngRepository ongRepository) {
        this.accountNumberRepository = accountNumberRepository;
        this.ongRepository = ongRepository;
    }

    @Override
    public Optional<AccountNumber> handle(GetAccountNumberByIdQuery query) {
        return accountNumberRepository.findById(query.id());
    }

    @Override
    public List<AccountNumber> handle(GetAllAccountNumberByOngIdQuery query) {
        Ong ong = ongRepository.findById(query.ongId())
                .orElseThrow(() -> new IllegalArgumentException("Ong with id " + query.ongId() + " not found"));
        return accountNumberRepository.findByOngId(ong);
    }
}
