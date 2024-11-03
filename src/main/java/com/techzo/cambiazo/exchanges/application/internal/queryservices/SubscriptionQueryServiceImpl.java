package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.Subscription;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllSubscriptionsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetSubscriptionByIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetActiveSubscriptionByUserIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.ISubscriptionQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.ISubscriptionRepository;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SubscriptionQueryServiceImpl implements ISubscriptionQueryService {

    private final ISubscriptionRepository subscriptionRepository;

    private final UserRepository userRepository;

    public SubscriptionQueryServiceImpl(ISubscriptionRepository subscriptionRepository, UserRepository userRepository){
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Subscription> handle(GetSubscriptionByIdQuery query) {
        return this.subscriptionRepository.findById(query.id());
    }

    @Override
    public Optional<Subscription> handle(GetActiveSubscriptionByUserIdQuery query) {
        User user = this.userRepository.findById(query.userId())
                .orElseThrow(() -> new IllegalArgumentException("User with id "+query.userId()+" not found"));
        return this.subscriptionRepository.findSubscriptionActiveByUserId(user);
    }

    @Override
    public List<Subscription> handle(GetAllSubscriptionsQuery query) {
        return this.subscriptionRepository.findAll();
    }
}
