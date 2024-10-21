package com.techzo.cambiazo.exchanges.domain.model.dtos;

import com.techzo.cambiazo.exchanges.domain.model.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String description;
    private String desiredObject;
    private Double price;
    private String image;
    private Boolean boost;
    private Boolean available;
    private ProductCategory productCategoryId;
    private Location location;
}

