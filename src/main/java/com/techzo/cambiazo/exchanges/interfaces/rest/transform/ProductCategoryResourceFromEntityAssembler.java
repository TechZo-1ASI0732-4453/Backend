package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.entities.ProductCategory;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ProductCategoryResource;

public class ProductCategoryResourceFromEntityAssembler {
    public static ProductCategoryResource toResourceFromEntity(ProductCategory entity) {
        return new ProductCategoryResource(entity.getId(), entity.getName());
    }
}
