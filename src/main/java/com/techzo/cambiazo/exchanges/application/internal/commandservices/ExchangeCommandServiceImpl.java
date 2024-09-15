package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateExchangeCommand;
import com.techzo.cambiazo.exchanges.domain.model.commands.UpdateExchangeStatusCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.domain.services.IExchangeCommandService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IExchangeRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ExchangeCommandServiceImpl implements IExchangeCommandService {

    private final IExchangeRepository exchangeRepository;

    private final IProductRepository productRepository;


    public ExchangeCommandServiceImpl(IExchangeRepository exchangeRepository, IProductRepository productRepository) {
        this.exchangeRepository = exchangeRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Exchange>handle(CreateExchangeCommand command) {
        Product productOwn= productRepository.findById(command.productOwnId()).orElseThrow(() -> new IllegalArgumentException("Product Own not found"));
        Product productChange = productRepository.findById(command.productChangeId()).orElseThrow(() -> new IllegalArgumentException("Product Change not found"));
        var exchange = new Exchange(command, productOwn, productChange);
        var createdExchange = exchangeRepository.save(exchange);
        return Optional.of(createdExchange);
    }

    @Override
    public Optional<Exchange>handle(UpdateExchangeStatusCommand command){
        var result = exchangeRepository.findById(command.id());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Exchange does not exist");
        }
        var exchangeToUpdate = result.get();
        try {
            Exchange exchange = exchangeRepository.findById(command.id())
                    .orElseThrow(() -> new IllegalArgumentException("Exchange not found"));

            Product productOwn = productRepository.findById(exchange.getProductOwnId())
                    .orElseThrow(() -> new IllegalArgumentException("Product Own not found"));

            Product productChange = productRepository.findById(exchange.getProductChangeId())
                    .orElseThrow(() -> new IllegalArgumentException("Product Change not found"));

            var updatedExchange = exchangeRepository.save(exchangeToUpdate.updateInformation(productOwn, productChange, command.status()));

            if("Aceptado".equals(command.status())){
                productOwn.setAvailable(false);
                productChange.setAvailable(false);
                productRepository.save(productOwn);
                productRepository.save(productChange);
            }

            return Optional.of(updatedExchange);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating exchange:" + e.getMessage());
        }
    }

    @Override
    public boolean handleDeleteExchange(Long id){
        Optional<Exchange>exchange = exchangeRepository.findById(id);
        if(exchange.isPresent()){
            exchangeRepository.delete(exchange.get());
            return true;
        }else {
            throw new IllegalArgumentException("Exchange not found");
        }
    }
}
