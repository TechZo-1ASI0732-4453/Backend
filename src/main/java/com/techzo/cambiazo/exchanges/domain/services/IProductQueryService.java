package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByProductCategoryIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByUserIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetProductByIdQuery;

import java.util.List;
import java.util.Optional;

public interface IProductQueryService {

    Optional<Product>handle(GetProductByIdQuery query);

    List<Product>handle(GetAllProductsQuery query);

    List<Product>handle(GetAllProductsByUserIdQuery query);

    List<Product>handle(GetAllProductsByProductCategoryIdQuery query);
}
