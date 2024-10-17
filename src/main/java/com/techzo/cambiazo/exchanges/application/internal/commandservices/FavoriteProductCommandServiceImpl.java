package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateFavoriteProductCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.FavoriteProduct;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.domain.services.IFavoriteProductCommandService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IFavoriteProductRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductRepository;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteProductCommandServiceImpl implements IFavoriteProductCommandService {

    private final IFavoriteProductRepository favoriteProductRepository;

    private final UserRepository userRepository;

    private final IProductRepository productRepository;

    public FavoriteProductCommandServiceImpl(IFavoriteProductRepository favoriteProductRepository, UserRepository userRepository, IProductRepository productRepository) {
        this.favoriteProductRepository = favoriteProductRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Optional<FavoriteProduct>handle(CreateFavoriteProductCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = productRepository.findById(command.productId()).orElseThrow(()-> new IllegalArgumentException("Product not found"));

        var favoriteProduct = new FavoriteProduct(product,user);
        favoriteProductRepository.save(favoriteProduct);
        return Optional.of(favoriteProduct);
    }

    @Override
    public boolean handleDeleteFavoriteProductByUserIdAndProductId(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = productRepository.findById(productId).orElseThrow(()-> new IllegalArgumentException("Product not found"));

        Optional<FavoriteProduct>favoriteProduct = favoriteProductRepository.findFavoriteProductByUserIdAndProductId(user,product);
        if(favoriteProduct.isPresent()){
            favoriteProductRepository.delete(favoriteProduct.get());
            return true;
        }else{
            return false;
        }
    }
}
