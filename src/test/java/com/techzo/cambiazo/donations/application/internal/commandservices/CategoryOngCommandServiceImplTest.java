package com.techzo.cambiazo.donations.application.internal.commandservices;

import com.techzo.cambiazo.donations.domain.model.commands.CreateCategoryOngCommand;
import com.techzo.cambiazo.donations.domain.model.commands.UpdateCategoryOngCommand;
import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.CategoryOngRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryOngCommandServiceImplTest {

    @Mock
    private CategoryOngRepository categoryOngRepository;

    @InjectMocks
    private CategoryOngCommandServiceImpl categoryOngCommandService;

    public CategoryOngCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_ShouldCreateCategoryOng_WhenValidCommand() {
        var command = new CreateCategoryOngCommand("Education");
        var categoryOng = new CategoryOng(command);

        when(categoryOngRepository.existsByName(command.name())).thenReturn(false);
        when(categoryOngRepository.save(any(CategoryOng.class))).thenReturn(categoryOng);

        var result = categoryOngCommandService.handle(command);

        assertTrue(result.isPresent());
        assertEquals(command.name(), result.get().getName());
        verify(categoryOngRepository, times(1)).existsByName(command.name());
        verify(categoryOngRepository, times(1)).save(any(CategoryOng.class));
    }

    @Test
    void handle_ShouldThrowException_WhenCategoryNameAlreadyExists() {
        var command = new CreateCategoryOngCommand("Education");

        when(categoryOngRepository.existsByName(command.name())).thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class, () -> categoryOngCommandService.handle(command));
        assertEquals("Category Ong with same name already exists", exception.getMessage());
        verify(categoryOngRepository, times(1)).existsByName(command.name());
    }

    @Test
    void handle_ShouldUpdateCategoryOng_WhenValidCommand() {
        var command = new UpdateCategoryOngCommand(1L, "Health");
        var existingCategoryOng = new CategoryOng("Education");

        when(categoryOngRepository.existsByNameAndIdIsNot(command.name(), command.id())).thenReturn(false);
        when(categoryOngRepository.findById(command.id())).thenReturn(Optional.of(existingCategoryOng));
        when(categoryOngRepository.save(any(CategoryOng.class))).thenReturn(existingCategoryOng);

        var result = categoryOngCommandService.handle(command);

        assertTrue(result.isPresent());
        assertEquals(command.name(), result.get().getName());
        verify(categoryOngRepository, times(1)).existsByNameAndIdIsNot(command.name(), command.id());
        verify(categoryOngRepository, times(1)).findById(command.id());
        verify(categoryOngRepository, times(1)).save(any(CategoryOng.class));
    }

    @Test
    void handle_ShouldThrowException_WhenUpdatingToExistingCategoryName() {
        var command = new UpdateCategoryOngCommand(1L, "Health");

        when(categoryOngRepository.existsByNameAndIdIsNot(command.name(), command.id())).thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class, () -> categoryOngCommandService.handle(command));
        assertEquals("Category Ong with same name already exists", exception.getMessage());
        verify(categoryOngRepository, times(1)).existsByNameAndIdIsNot(command.name(), command.id());
    }

    @Test
    void handleDeleteCategoryOng_ShouldReturnTrue_WhenCategoryExists() {
        var categoryOng = new CategoryOng("Education");

        when(categoryOngRepository.findById(1L)).thenReturn(Optional.of(categoryOng));

        var result = categoryOngCommandService.handleDeleteCategoryOng(1L);

        assertTrue(result);
        verify(categoryOngRepository, times(1)).findById(1L);
        verify(categoryOngRepository, times(1)).delete(categoryOng);
    }

    @Test
    void handleDeleteCategoryOng_ShouldReturnFalse_WhenCategoryDoesNotExist() {
        when(categoryOngRepository.findById(1L)).thenReturn(Optional.empty());

        var result = categoryOngCommandService.handleDeleteCategoryOng(1L);

        assertFalse(result);
        verify(categoryOngRepository, times(1)).findById(1L);
        verify(categoryOngRepository, never()).delete(any(CategoryOng.class));
    }
}