package com.techzo.cambiazo.exchanges.domain.model.entities;


import com.techzo.cambiazo.exchanges.domain.model.commands.CreateExchangeCommand;
import com.techzo.cambiazo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@Entity
public class Exchange extends AuditableAbstractAggregateRoot<Exchange> {

    @Column(nullable = true)
    private LocalDate exchangeDate;

    @ManyToOne
    @JoinColumn(name = "product_own_id", nullable = false)
    @NotNull(message = "Product Own ID is mandatory")
    private Product productOwnId;

    @ManyToOne
    @JoinColumn(name = "product_change_id", nullable = false)
    @NotNull(message = "Product Change ID is mandatory")
    private Product productChangeId;

    @Column(nullable = false)
    @NotNull(message = "Status is mandatory")
    private String status;


    private Long userOwnId;

    private Long userChangeId;

    public Exchange() {
    }

    public Exchange(CreateExchangeCommand command, Product productOwnId, Product productChangeId) {
        this.exchangeDate=null;
        this.productOwnId = productOwnId;
        this.productChangeId = productChangeId;
        this.status = command.status();
    }

    public Exchange updateInformation(Product productOwnId, Product productChangeId, String status) {
        this.productOwnId = productOwnId;
        this.productChangeId = productChangeId;
        this.status = status;
        return this;
    }

    public Long getProductOwnId() {
        return productOwnId.getId();
    }

    public Long getProductChangeId() {
        return productChangeId.getId();
    }

    public Long getUserOwnId() {
        return productOwnId.getUserId();
    }

    public Long getUserChangeId() {
        return productChangeId.getUserId();
    }



}
