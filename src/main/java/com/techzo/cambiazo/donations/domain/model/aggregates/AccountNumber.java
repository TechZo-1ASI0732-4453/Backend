package com.techzo.cambiazo.donations.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.techzo.cambiazo.donations.domain.model.commands.CreateAccountNumberCommand;
import com.techzo.cambiazo.donations.domain.model.valueobjects.AccountNumberAccount;
import com.techzo.cambiazo.donations.domain.model.valueobjects.AccountNumberCci;
import com.techzo.cambiazo.donations.domain.model.valueobjects.AccountNumberName;
import com.techzo.cambiazo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Represents an Account Number entity related to an Ong.
 *
 * @author CambiaZo - TechZo
 * @version 1.0
 */
@Entity
public class AccountNumber extends AuditableAbstractAggregateRoot<AccountNumber>{

    @Embedded
    @Column(nullable = false)
    @NotNull(message = "Name is mandatory")
    private AccountNumberName name;

    @Embedded
    @Column(nullable = false)
    @NotNull(message = "CCI is mandatory")
    private AccountNumberCci cci;

    @Embedded
    @Column(nullable = false)
    @NotNull(message = "Account Number is mandatory")
    private AccountNumberAccount account;

    @ManyToOne
    @JoinColumn(name = "ong_id")
    @NotNull(message = "Ong is mandatory")
    private Ong ongId;

    public AccountNumber() {}

    /**
     * Constructor for creating an AccountNumber entity.
     *
     * @param command The command containing data to create the AccountNumber.
     * @param ong The Ong associated with this AccountNumber.
     */
    public AccountNumber(CreateAccountNumberCommand command, Ong ong) {
        this.name = new AccountNumberName(command.name());
        this.cci = new AccountNumberCci(command.cci());
        this.account = new AccountNumberAccount(command.account());
        this.ongId = ong;
    }

    /**
     * Get the ID of the associated Ong.
     *
     * @return The ID of the Ong.
     */
    public Long getOngId() {
        return ongId.getId();
    }

    /**
     * Get the name of the account number.
     *
     * @return The name of the account number.
     */
    public String getName() {
        return name.getAccountNumberName();
    }

    /**
     * Get the CCI (Código de Cuenta Interbancario) of the account number.
     *
     * @return The CCI of the account number.
     */
    public String getCci() {
        return cci.getAccountNumberCci();
    }

    /**
     * Get the account number.
     *
     * @return The account number.
     */
    public String getAccount() {
        return account.getAccountNumberAccount();
    }

}
