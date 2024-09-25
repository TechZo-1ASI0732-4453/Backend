package com.techzo.cambiazo.donations.domain.services;

import com.techzo.cambiazo.donations.domain.model.commands.CreateCategoryOngCommand;
import com.techzo.cambiazo.donations.domain.model.commands.UpdateCategoryOngCommand;
import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;

import java.util.Optional;

public interface CategoryOngCommandService {
    Optional<CategoryOng> handle(CreateCategoryOngCommand command);
    Optional<CategoryOng>handle(UpdateCategoryOngCommand command);
    boolean handleDeleteCategoryOng(Long id);
}
