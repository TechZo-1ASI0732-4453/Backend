package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.dtos.ModifiedExchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface IExchangeQueryService {

    Optional<ModifiedExchange>handle(GetExchangeByIdQuery query);

    List<ModifiedExchange>handle(GetAllExchangesQuery query);

    List<ModifiedExchange>handle(GetAllExchangesByUserOwnIdQuery query);

    List<ModifiedExchange>handle(GetAllExchangesByUserChangeIdQuery query);

    List<ModifiedExchange>handle(GetAllFinishedExchangesByUserIdQuery query);
}
