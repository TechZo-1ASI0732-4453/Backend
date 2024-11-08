package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateProductCommand;
import com.techzo.cambiazo.exchanges.domain.model.commands.DeleteProductOfPendingExchangesCommand;
import com.techzo.cambiazo.exchanges.domain.model.commands.UpdateProductCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;

import java.util.Optional;

public interface IProductCommandService {
    Optional<Product>handle(CreateProductCommand command);

    Optional<Product>handle(UpdateProductCommand command);
    boolean handleDeleteProduct(DeleteProductOfPendingExchangesCommand command);
}
