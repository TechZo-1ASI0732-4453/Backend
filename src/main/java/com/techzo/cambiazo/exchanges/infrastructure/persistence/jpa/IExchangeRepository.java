package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;

import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExchangeRepository extends JpaRepository<Exchange, Long> {

    Exchange findExchangeByProductOwnIdAndProductChangeId(Product productOwnId, Product productChangeId);

    List<Exchange>findAllExchangesByProductOwnId_UserId(User userOwnId);

    List<Exchange>findAllExchangesByProductChangeId_UserId(User userChangeId);

    @Modifying
    @Query("UPDATE Exchange p SET p.status = 'Rechazado' WHERE p.productOwnId = :product AND p.id <> :exchangeId")
    void updateExchangeStatusToRejectedByProductOwnExcept(@Param("product") Product product, @Param("exchangeId") Long exchangeId);

    @Modifying
    @Query("UPDATE Exchange p SET p.status = 'Rechazado' WHERE p.productChangeId = :product AND p.id <> :exchangeId")
    void updateExchangeStatusToRejectedByProductChangeExcept(@Param("product") Product product, @Param("exchangeId") Long exchangeId);

}
