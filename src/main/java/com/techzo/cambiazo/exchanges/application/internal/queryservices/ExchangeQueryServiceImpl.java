package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.dtos.ModifiedExchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.domain.model.queries.*;
import com.techzo.cambiazo.exchanges.domain.services.IExchangeQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IExchangeRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductRepository;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.techzo.cambiazo.iam.interfaces.rest.transform.UserResource2FromEntityAssembler;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

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
    public Optional<ModifiedExchange> handle(GetExchangeByIdQuery query) {
        Exchange exchange= this.exchangeRepository.findById(query.id())
                .orElseThrow(() -> new IllegalArgumentException("Exchange not found"));
        Product productOwn = this.productRepository.findById(exchange.getProductOwnId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Product productChange = this.productRepository.findById(exchange.getProductChangeId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        User userOwn = this.userRepository.findById(productOwn.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User userChange = this.userRepository.findById(productChange.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var userOwnResource = UserResource2FromEntityAssembler.toResourceFromEntity(userOwn);
        var userChangeResource = UserResource2FromEntityAssembler.toResourceFromEntity(userChange);

        return Optional.of(
                new ModifiedExchange(
                        exchange,
                        productOwn,
                        productChange,
                        userOwnResource,
                        userChangeResource
                )
        );
    }

    @Override
    public List<ModifiedExchange> handle(GetAllExchangesQuery query) {
        List<User> users = this.userRepository.findAll();
        List<Product> products = this.productRepository.findAll();
        List<Exchange> exchanges = this.exchangeRepository.findAll();


        return exchanges.stream().map(exchange -> {
            Product productOwn = products.stream()
                    .filter(product -> product.getId().equals(exchange.getProductOwnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            Product productChange = products.stream()
                    .filter(product -> product.getId().equals(exchange.getProductChangeId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            User userOwn = users.stream()
                    .filter(user -> user.getId().equals(productOwn.getUserId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            User userChange = users.stream()
                    .filter(user -> user.getId().equals(productChange.getUserId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            var userOwnResource = UserResource2FromEntityAssembler.toResourceFromEntity(userOwn);
            var userChangeResource = UserResource2FromEntityAssembler.toResourceFromEntity(userChange);

            return new ModifiedExchange(
                    exchange,
                    productOwn,
                    productChange,
                    userOwnResource,
                    userChangeResource
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<ModifiedExchange> handle(GetAllExchangesByUserOwnIdQuery query) {
        User userOwn = this.userRepository.findById(query.id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var userOwnResource = UserResource2FromEntityAssembler.toResourceFromEntity(userOwn);
        
        List<Product>products = this.productRepository.findAll();
        List<Exchange>exchanges = this.exchangeRepository.findAllExchangesByProductOwnId_UserId(userOwn);

        return exchanges.stream().map(exchange -> {
            Product productOwn = products.stream()
                    .filter(product -> product.getId().equals(exchange.getProductOwnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            Product productChange = products.stream()
                    .filter(product -> product.getId().equals(exchange.getProductChangeId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            User userChange = this.userRepository.findById(productChange.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            
            var userChangeResource = UserResource2FromEntityAssembler.toResourceFromEntity(userChange);
            return new ModifiedExchange(
                    exchange,
                    productOwn,
                    productChange,
                    userOwnResource,
                    userChangeResource
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<ModifiedExchange> handle(GetAllExchangesByUserChangeIdQuery query) {
        User user = this.userRepository.findById(query.id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        var userChangeResource = UserResource2FromEntityAssembler.toResourceFromEntity(user);

        List<Product>products = this.productRepository.findAll();
        List<Exchange>exchanges = this.exchangeRepository.findAllExchangesByProductChangeId_UserId(user);

        return exchanges.stream().map(exchange -> {
            Product productOwn = products.stream()
                    .filter(product -> product.getId().equals(exchange.getProductOwnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            Product productChange = products.stream()
                    .filter(product -> product.getId().equals(exchange.getProductChangeId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            User userOwn = this.userRepository.findById(productOwn.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            var userOwnResource = UserResource2FromEntityAssembler.toResourceFromEntity(userOwn);

            return new ModifiedExchange(
                    exchange,
                    productOwn,
                    productChange,
                    userOwnResource,
                    userChangeResource
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<ModifiedExchange> handle(GetAllFinishedExchangesByUserIdQuery query) {
        User user = this.userRepository.findById(query.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var userOwnResource = UserResource2FromEntityAssembler.toResourceFromEntity(user);

        List<Product>products=this.productRepository.findAll();

        List<Exchange> exchangesOwn = this.exchangeRepository.findAllExchangesByProductOwnId_UserId(user)
                .stream()
                .filter(exchange -> "Aceptado".equals(exchange.getStatus()))
                .toList();

        List<Exchange> exchangesChange = this.exchangeRepository.findAllExchangesByProductChangeId_UserId(user)
                .stream()
                .filter(exchange -> "Aceptado".equals(exchange.getStatus()))
                .toList();


        List<ModifiedExchange> modifiedExchangesOwn = exchangesOwn.stream().map(exchange -> {
            Product productOwn = products.stream()
                    .filter(product -> product.getId().equals(exchange.getProductOwnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            Product productChange = products.stream()
                    .filter(product -> product.getId().equals(exchange.getProductChangeId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            User userChange = this.userRepository.findById(productChange.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            var userChangeResource = UserResource2FromEntityAssembler.toResourceFromEntity(userChange);

            return new ModifiedExchange(
                    exchange,
                    productOwn,
                    productChange,
                    userOwnResource,
                    userChangeResource
            );
        }).collect(Collectors.toList());


        List<ModifiedExchange>modifiedExchangesChange=exchangesChange.stream().map(exchange -> {
            Product productOwn = products.stream()
                    .filter(product -> product.getId().equals(exchange.getProductOwnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            Product productChange = products.stream()
                    .filter(product -> product.getId().equals(exchange.getProductChangeId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            User userChange = this.userRepository.findById(productOwn.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            var userChangeResource = UserResource2FromEntityAssembler.toResourceFromEntity(userChange);

            return new ModifiedExchange(
                    exchange,
                    productOwn,
                    productChange,
                    userChangeResource,
                    userOwnResource
            );
        }).toList();


        // Combine both lists
        modifiedExchangesOwn.addAll(modifiedExchangesChange);

        return modifiedExchangesOwn.stream()
                .sorted(Comparator.comparing(ModifiedExchange::getExchangeDate).reversed())
                .collect(Collectors.toList());
    }

}
