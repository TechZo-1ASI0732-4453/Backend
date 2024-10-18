package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;

import com.techzo.cambiazo.exchanges.domain.model.entities.FavoriteProduct;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {

    List<FavoriteProduct>findFavoriteProductsByUserId(User userId);

    Optional<FavoriteProduct>findFavoriteProductByUserIdAndProductId(User userId, Product productId);

    boolean existsByUserIdAndProductId(User userId, Product productId);

    boolean existsFavoriteProductById(Long id);
}
