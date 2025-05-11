package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;

import com.techzo.cambiazo.exchanges.domain.model.entities.Invoice;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    List<Invoice> findByUserId(User user);
}
