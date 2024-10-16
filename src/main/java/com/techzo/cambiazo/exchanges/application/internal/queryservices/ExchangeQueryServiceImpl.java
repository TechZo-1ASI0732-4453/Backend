package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.domain.model.queries.*;
import com.techzo.cambiazo.exchanges.domain.services.IExchangeQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IExchangeRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductRepository;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<Exchange> handle(GetAllFinishedExchangesByUserIdQuery query) {
        User user = this.userRepository.findById(query.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Exchange> exchangesOwn = this.exchangeRepository.findAllExchangesByProductOwnId_UserId(user)
                .stream()
                .filter(exchange -> "ACEPTADO".equals(exchange.getStatus()))
                .collect(Collectors.toList());

        List<Exchange> exchangesChange = this.exchangeRepository.findAllExchangesByProductChangeId_UserId(user)
                .stream()
                .filter(exchange -> "ACEPTADO".equals(exchange.getStatus()))
                .collect(Collectors.toList());

        // Swap productOwn and productChange for exchanges where the user is userChange
        List<Exchange> modifiedExchangesChange = exchangesChange.stream().map(exchange -> {
            Exchange modifiedExchange = new Exchange();
            Product productChange = this.productRepository.findById(exchange.getProductChangeId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            Product productOwn = this.productRepository.findById(exchange.getProductOwnId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            modifiedExchange.setProductOwnId(productChange);
            modifiedExchange.setProductChangeId(productOwn);
            modifiedExchange.setStatus(exchange.getStatus());
            modifiedExchange.setExchangeDate(exchange.getExchangeDate());
            return modifiedExchange;
        }).collect(Collectors.toList());

        // Combine both lists
        exchangesOwn.addAll(modifiedExchangesChange);
        return exchangesOwn.stream()
                .sorted(Comparator.comparing(Exchange::getExchangeDate))
                .collect(Collectors.toList());
    }

}
