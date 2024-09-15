package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateProductCategoryCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.ProductCategory;
import com.techzo.cambiazo.exchanges.domain.services.IProductCategoryCommandService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCategoryCommandServiceImpl implements IProductCategoryCommandService {
    private final IProductCategoryRepository productCategoryRepository;

    public ProductCategoryCommandServiceImpl(IProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public Optional<ProductCategory> handle(CreateProductCategoryCommand command) {
        if (productCategoryRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Product category with same name already exists");
        }
        var productCategory = new ProductCategory(command);
        var createdProductCategory = productCategoryRepository.save(productCategory);
        return Optional.of(createdProductCategory);
    }
}
