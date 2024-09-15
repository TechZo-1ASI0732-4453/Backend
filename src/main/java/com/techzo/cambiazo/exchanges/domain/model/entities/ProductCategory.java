package com.techzo.cambiazo.exchanges.domain.model.entities;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateProductCategoryCommand;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false)
    @Getter
    private String name;

    protected ProductCategory() {}

    public ProductCategory(CreateProductCategoryCommand command) {
        this.name = command.name();
    }


}
