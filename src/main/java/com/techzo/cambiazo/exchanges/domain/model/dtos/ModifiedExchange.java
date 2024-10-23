package com.techzo.cambiazo.exchanges.domain.model.dtos;

import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.iam.interfaces.rest.resources.UserResource2;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Data
@AllArgsConstructor
public class ModifiedExchange {

    private Long id;
    private Product productOwn;
    private Product productChange;
    private UserResource2 userOwn;
    private UserResource2 userChange;
    private String status;
    private LocalDate exchangeDate;
    private Date createdAt;
    private Date updatedAt;

    public ModifiedExchange(){}


    public ModifiedExchange(Exchange exchange, Product productOwn, Product productChange, UserResource2 userOwn, UserResource2 userChange) {
        this.id = exchange.getId();
        this.productOwn = productOwn;
        this.productChange = productChange;
        this.status = exchange.getStatus();
        this.exchangeDate = exchange.getExchangeDate();
        this.createdAt = exchange.getCreatedAt();
        this.updatedAt = exchange.getUpdatedAt();
        this.userOwn = userOwn;
        this.userChange = userChange;
    }
}
