package com.techzo.cambiazo.donations.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techzo.cambiazo.donations.domain.model.aggregates.AccountNumber;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.valueobjects.AccountNumberAccount;
import com.techzo.cambiazo.donations.domain.model.valueobjects.AccountNumberCci;
import com.techzo.cambiazo.donations.domain.model.valueobjects.AccountNumberName;

import java.util.List;
import java.util.Optional;

public interface AccountNumberRepository extends JpaRepository<AccountNumber, Long>{

    Optional<AccountNumber>findByNameAndCciAndAccount(AccountNumberName name, AccountNumberCci cci, AccountNumberAccount account);

    List<AccountNumber>findByOngId(Ong id);
}
