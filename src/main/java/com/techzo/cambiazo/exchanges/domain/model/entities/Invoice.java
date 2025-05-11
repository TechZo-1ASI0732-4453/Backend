package com.techzo.cambiazo.exchanges.domain.model.entities;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateInvoiceCommand;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
public class Invoice extends AuditableAbstractAggregateRoot<Invoice> {

    @Column(nullable = false, unique = true)
    @NotNull(message = "InvoiceNumber is required")
    private String invoiceNumber;

    @Column(nullable = false)
    @NotNull(message = "Amount is required")
    private Double amount;

    @Column(nullable = false)
    @NotNull(message = "Description is required")
    private String description;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = true)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    public Invoice() {
    }

    public Invoice(String invoiceNumber, Double amount, String description, String filePath, User user) {
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.description = description;
        this.issuedAt = LocalDateTime.now();
        this.filePath = filePath;
        this.userId = user;
    }

    public Long getUserId() {
        return userId.getId();
    }

    public User getUser() {
        return userId;
    }

}
