package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.ProductCategory;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductCategoriesQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetProductCategoryByIdQuery;

import java.util.List;
import java.util.Optional;

public interface IProductCategoryQueryService {

    Optional<ProductCategory>handle(GetProductCategoryByIdQuery query);

    List<ProductCategory>handle(GetAllProductCategoriesQuery query);
}
