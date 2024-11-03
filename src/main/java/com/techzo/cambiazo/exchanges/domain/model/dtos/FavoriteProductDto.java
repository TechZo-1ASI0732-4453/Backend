package com.techzo.cambiazo.exchanges.domain.model.dtos;

import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoriteProductDto {
    private Long id;

    private ProductDto product;

    private Long userId;

    public FavoriteProductDto(){
    }

}
