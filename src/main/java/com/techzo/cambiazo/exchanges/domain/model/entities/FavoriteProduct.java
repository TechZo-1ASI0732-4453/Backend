package com.techzo.cambiazo.exchanges.domain.model.entities;
import com.techzo.cambiazo.exchanges.domain.model.commands.CreateFavoriteProductCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class FavoriteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id" ,nullable = false)
    private Product productId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    public FavoriteProduct() {
    }

    public FavoriteProduct(CreateFavoriteProductCommand command, Product product, User user) {
        this.productId = product;
        this.userId = user;
    }

    public Long getProductId() {
        return productId.getId();
    }

    public Long getUserId() {
        return userId.getId();
    }
}
