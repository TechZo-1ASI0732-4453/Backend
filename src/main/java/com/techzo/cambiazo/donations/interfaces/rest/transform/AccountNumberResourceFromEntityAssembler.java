package com.techzo.cambiazo.donations.interfaces.rest.transform;

import com.techzo.cambiazo.donations.domain.model.aggregates.AccountNumber;
import com.techzo.cambiazo.donations.interfaces.rest.resources.AccountNumberResource;

public class AccountNumberResourceFromEntityAssembler {

    public static AccountNumberResource toResourceFromEntity(AccountNumber entity) {
        return new AccountNumberResource(entity.getId(), entity.getName(), entity.getCci(), entity.getAccount(), entity.getOngId());
    }
}
