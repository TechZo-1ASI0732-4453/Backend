package com.techzo.cambiazo.donations.application.internal.queryservices;

import org.springframework.stereotype.Service;
import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllCategoryOngsQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetCategoryOngByIdQuery;
import com.techzo.cambiazo.donations.domain.services.CategoryOngQueryService;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.CategoryOngRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryOngQueryServiceImpl implements CategoryOngQueryService {
    private final CategoryOngRepository categoryOngRepository;

    public CategoryOngQueryServiceImpl(CategoryOngRepository categoryOngRepository) {
        this.categoryOngRepository = categoryOngRepository;
    }

    @Override
    public List<CategoryOng> handle(GetAllCategoryOngsQuery query) {
        return categoryOngRepository.findAll();
    }

    @Override
    public Optional<CategoryOng> handle(GetCategoryOngByIdQuery query) {
        return categoryOngRepository.findById(query.id());
    }
}
