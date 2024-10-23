package com.techzo.cambiazo.exchanges.domain.model.dtos;

import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
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

    private LocalDate exchangeDate;

    private Long productOwnId;

    private Long productChangeId;

    private String status;

    private Date createdAt;
    private Date updatedAt;

    public ModifiedExchange(){}

    public ModifiedExchange(Exchange exchange) {
        this.id = exchange.getId();
        this.exchangeDate = exchange.getExchangeDate();
        this.productOwnId = exchange.getProductOwnId();
        this.productChangeId = exchange.getProductChangeId();
        this.status = exchange.getStatus();
        this.createdAt = exchange.getCreatedAt();
        this.updatedAt = exchange.getUpdatedAt();
    }
}
