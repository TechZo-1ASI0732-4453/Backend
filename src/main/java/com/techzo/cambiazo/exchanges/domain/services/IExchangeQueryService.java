package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface IExchangeQueryService {

    Optional<Exchange>handle(GetExchangeByIdQuery query);

    List<Exchange>handle(GetAllExchangesQuery query);

    List<Exchange>handle(GetAllExchangesByUserOwnIdQuery query);

    List<Exchange>handle(GetAllExchangesByUserChangeIdQuery query);

    List<Exchange>handle(GetAllFinishedExchangesByUserIdQuery query);
}
