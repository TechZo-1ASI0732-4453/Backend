package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.dtos.ProductDto;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByProductCategoryIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByUserIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetProductByIdQuery;

import java.util.List;
import java.util.Optional;

public interface IProductQueryService {

    Optional<ProductDto>handle(GetProductByIdQuery query);

    List<ProductDto>handle(GetAllProductsQuery query);

    List<ProductDto>handle(GetAllProductsByUserIdQuery query);

    List<ProductDto>handle(GetAllProductsByProductCategoryIdQuery query);
}
