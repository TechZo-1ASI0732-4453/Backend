package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.Subscription;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllSubscriptionsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetSubscriptionByIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetSubscriptionByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface ISubscriptionQueryService {

    Optional<Subscription>handle(GetSubscriptionByIdQuery query);

    Optional<Subscription>handle(GetSubscriptionByUserIdQuery query);
    List<Subscription>handle(GetAllSubscriptionsQuery query);
}
