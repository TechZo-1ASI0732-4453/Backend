package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;


import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.domain.model.entities.ProductCategory;
import com.techzo.cambiazo.exchanges.domain.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameAndId(String name, Long id);

    List<Product>findProductsByUserId(User userId);

    List<Product>findProductsByProductCategoryId(ProductCategory productCategoryId);
}
