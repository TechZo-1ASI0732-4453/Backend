package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;

import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExchangeRepository extends JpaRepository<Exchange, Long> {

    List<Exchange>findAllExchangesByProductOwnId_UserId(User userOwnId);

    List<Exchange>findAllExchangesByProductChangeId_UserId(User userChangeId);
}
