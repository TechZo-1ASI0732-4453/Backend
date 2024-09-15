package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.domain.model.entities.ProductCategory;
import com.techzo.cambiazo.exchanges.domain.model.entities.User;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByProductCategoryIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByUserIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetProductByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IProductQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductCategoryRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductQueryServiceImpl implements IProductQueryService {

    private final IProductRepository productRepository;

    private final IUserRepository userRepository;

    private final IProductCategoryRepository productCategoryRepository;

    public ProductQueryServiceImpl(IProductRepository productRepository, IUserRepository userRepository, IProductCategoryRepository productCategoryRepository){

        this.productRepository=productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Optional<Product> handle(GetProductByIdQuery query) {
        return productRepository.findById(query.id());
    }

    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        return productRepository.findAll();
    }

    @Override
    public List<Product> handle(GetAllProductsByUserIdQuery query) {
        User user = userRepository.findById(query.userId())
                .orElseThrow(() -> new IllegalArgumentException("User with id "+query.userId()+" not found"));
        return productRepository.findProductsByUserId(user);
    }

    @Override
    public List<Product> handle(GetAllProductsByProductCategoryIdQuery query) {
        ProductCategory productCategory = productCategoryRepository.findById(query.productCategoryId())
                .orElseThrow(()->new IllegalArgumentException("Product Category with id "+query.productCategoryId()+" not found"));
        return productRepository.findProductsByProductCategoryId(productCategory);
    }
}
