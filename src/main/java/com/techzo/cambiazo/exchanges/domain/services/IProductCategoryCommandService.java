package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateProductCategoryCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.ProductCategory;

import java.util.Optional;

public interface IProductCategoryCommandService {
    Optional<ProductCategory>handle(CreateProductCategoryCommand command);
}
