package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;

import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import jakarta.transaction.Transactional;
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

    @Query("SELECT e FROM Exchange e WHERE e.productOwnId = :productId OR e.productChangeId = :productId AND e.status = 'Pendiente'")
    List<Exchange>findAllExchangesByProductOwnIdOrProductChangeId(@Param("productId") Product productId);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
       UPDATE Exchange e
          SET e.status = 'Rechazado'
        WHERE e.productOwnId.id = :productId
          AND e.status = 'Pendiente'
          AND e.id <> :exchangeId
       """)
    int updateExchangeStatusToRejectedByProductOwnExcept(@Param("productId") Long productId,
                                                         @Param("exchangeId") Long exchangeId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
       UPDATE Exchange e
          SET e.status = 'Rechazado'
        WHERE e.productChangeId.id = :productId
          AND e.status = 'Pendiente'
          AND e.id <> :exchangeId
       """)
    int updateExchangeStatusToRejectedByProductChangeExcept(@Param("productId") Long productId,
                                                            @Param("exchangeId") Long exchangeId);

}
