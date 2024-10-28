package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;


import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.domain.model.entities.ProductCategory;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameAndId(String name, Long id);

    List<Product>findProductsByUserId(User userId);

    List<Product>findProductsByProductCategoryId(ProductCategory productCategoryId);

    @Modifying
    @Query("UPDATE Product p SET p.available = false WHERE p.userId = :user")
    void updateProductAvailabilityByUser(@Param("user") User user);
}
