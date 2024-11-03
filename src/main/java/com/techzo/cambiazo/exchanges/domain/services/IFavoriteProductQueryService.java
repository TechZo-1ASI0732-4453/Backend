package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.dtos.FavoriteProductDto;
import com.techzo.cambiazo.exchanges.domain.model.entities.FavoriteProduct;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllFavoriteProductsByUserIdQuery;

import java.util.List;

public interface IFavoriteProductQueryService {

    List<FavoriteProductDto>handle(GetAllFavoriteProductsByUserIdQuery query);
}
