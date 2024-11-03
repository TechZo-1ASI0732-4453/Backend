package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.Subscription;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetActiveSubscriptionByUserIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllSubscriptionsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetSubscriptionByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ISubscriptionQueryService {

    Optional<Subscription>handle(GetSubscriptionByIdQuery query);

    Optional<Subscription>handle(GetActiveSubscriptionByUserIdQuery query);
    List<Subscription>handle(GetAllSubscriptionsQuery query);
}
