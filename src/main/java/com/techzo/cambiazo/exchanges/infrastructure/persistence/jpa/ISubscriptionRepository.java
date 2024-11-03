package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;

import com.techzo.cambiazo.exchanges.domain.model.entities.Subscription;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubscriptionRepository extends JpaRepository<Subscription,Long> {

    List<Subscription>findAllByUserId(User userId);


    @Query("SELECT s FROM Subscription s WHERE s.userId = :userId AND s.state = 'Activo'")
    Optional<Subscription>findSubscriptionActiveByUserId(User userId);
}
