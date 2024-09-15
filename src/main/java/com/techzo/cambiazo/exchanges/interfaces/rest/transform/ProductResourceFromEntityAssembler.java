package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ProductResource;

public class ProductResourceFromEntityAssembler {

    public static ProductResource toResourceFromEntity(Product entity) {
        return new ProductResource(entity.getId(), entity.getName(), entity.getDescription(), entity.getDesiredObject(), entity.getPrice(), entity.getImage(), entity.getBoost(), entity.getAvailable(), entity.getProductCategoryId(), entity.getUserId(), entity.getDistrictId());
    }
}
