package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.FavoriteProduct;
import com.techzo.cambiazo.exchanges.domain.model.entities.User;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllFavoriteProductsByUserIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IFavoriteProductQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IFavoriteProductRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteProductQueryServiceImpl implements IFavoriteProductQueryService {

    private final IFavoriteProductRepository favoriteProductRepository;

    private final IUserRepository userRepository;

    public FavoriteProductQueryServiceImpl(IFavoriteProductRepository favoriteProductRepository, IUserRepository userRepository) {
        this.favoriteProductRepository = favoriteProductRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<FavoriteProduct> handle(GetAllFavoriteProductsByUserIdQuery query) {
        User user = this.userRepository.findById(query.userId())
                .orElseThrow(()->new IllegalArgumentException("User not found"));
        return this.favoriteProductRepository.findFavoriteProductsByUserId(user);
    }
}
