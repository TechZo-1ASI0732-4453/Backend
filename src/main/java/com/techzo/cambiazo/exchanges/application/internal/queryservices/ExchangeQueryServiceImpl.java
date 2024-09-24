package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllExchangesByUserChangeIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllExchangesByUserOwnIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllExchangesQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetExchangeByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IExchangeQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IExchangeRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductRepository;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangeQueryServiceImpl implements IExchangeQueryService {

    private final IExchangeRepository exchangeRepository;

    private final IProductRepository productRepository;
    private final UserRepository userRepository;

    public ExchangeQueryServiceImpl(IExchangeRepository exchangeRepository, UserRepository userRepository, IProductRepository productRepository) {
        this.exchangeRepository = exchangeRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Optional<Exchange> handle(GetExchangeByIdQuery query) {
        return this.exchangeRepository.findById(query.id());
    }

    @Override
    public List<Exchange> handle(GetAllExchangesQuery query) {
        return this.exchangeRepository.findAll();
    }

    @Override
    public List<Exchange> handle(GetAllExchangesByUserOwnIdQuery query) {
        User user = this.userRepository.findById(query.id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return this.exchangeRepository.findAllExchangesByProductOwnId_UserId(user);
    }

    @Override
    public List<Exchange> handle(GetAllExchangesByUserChangeIdQuery query) {
        User user = this.userRepository.findById(query.id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return this.exchangeRepository.findAllExchangesByProductChangeId_UserId(user);
    }
}
