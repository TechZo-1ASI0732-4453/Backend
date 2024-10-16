package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.FavoriteProduct;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllFavoriteProductsByUserIdQuery;

import java.util.List;

public interface IFavoriteProductQueryService {

    List<FavoriteProduct>handle(GetAllFavoriteProductsByUserIdQuery query);
}
