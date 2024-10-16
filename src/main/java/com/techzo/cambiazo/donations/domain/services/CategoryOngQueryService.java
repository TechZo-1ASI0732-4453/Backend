package com.techzo.cambiazo.donations.domain.services;

import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllCategoryOngsQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetCategoryOngByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CategoryOngQueryService {
    List<CategoryOng> handle(GetAllCategoryOngsQuery query);
    Optional<CategoryOng> handle(GetCategoryOngByIdQuery query);
}
