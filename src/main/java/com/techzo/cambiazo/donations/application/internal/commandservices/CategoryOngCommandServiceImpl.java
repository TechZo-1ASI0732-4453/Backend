package com.techzo.cambiazo.donations.application.internal.commandservices;

import org.springframework.stereotype.Service;
import com.techzo.cambiazo.donations.domain.model.commands.CreateCategoryOngCommand;
import com.techzo.cambiazo.donations.domain.model.commands.UpdateCategoryOngCommand;
import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;
import com.techzo.cambiazo.donations.domain.services.CategoryOngCommandService;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.CategoryOngRepository;

import java.util.Optional;

@Service
public class CategoryOngCommandServiceImpl implements CategoryOngCommandService {
    private final CategoryOngRepository categoryOngRepository;

    public CategoryOngCommandServiceImpl(CategoryOngRepository categoryOngRepository) {
        this.categoryOngRepository = categoryOngRepository;
    }

    @Override
    public Optional<CategoryOng>handle (CreateCategoryOngCommand command) {
        if (categoryOngRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Category Ong with same name already exists");
        }
        var categoryOng = new CategoryOng(command);
        var createdCategoryOng = categoryOngRepository.save(categoryOng);
        return Optional.of(createdCategoryOng);
    }

    @Override
    public Optional<CategoryOng>handle(UpdateCategoryOngCommand command){
        if (categoryOngRepository.existsByNameAndIdIsNot(command.name(), command.id())) {
            throw new IllegalArgumentException("Category Ong with same name already exists");
        }
        var result = categoryOngRepository.findById(command.id());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Category Ong does not exist");
        }
        var categoryOngToUpdate = result.get();
        try {
            var updatedCategoryOng = categoryOngRepository.save(categoryOngToUpdate.updateInformation(command.name()));
            return Optional.of(updatedCategoryOng);
        }catch (Exception e) {
            throw new IllegalArgumentException("Error while updating category Ong: " + e.getMessage());
        }


    }

    @Override
    public boolean handleDeleteCategoryOng(Long id) {
        Optional<CategoryOng> categoryOng = categoryOngRepository.findById(id);
        if (categoryOng.isPresent()) {
            categoryOngRepository.delete(categoryOng.get());
            return true;
        } else {
            return false;
        }
    }
}
