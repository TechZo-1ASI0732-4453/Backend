package com.techzo.cambiazo.exchanges.domain.model.dtos;

import com.techzo.cambiazo.exchanges.domain.model.entities.*;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String desiredObject;
    private Double price;
    private String image;
    private Boolean boost;
    private Boolean available;
    private Long userId;
    private ProductCategory productCategory;
    private Location location;
}

