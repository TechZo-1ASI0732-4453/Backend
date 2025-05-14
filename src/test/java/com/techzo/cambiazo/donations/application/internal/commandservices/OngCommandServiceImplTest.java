package com.techzo.cambiazo.donations.application.internal.commandservices;

import com.techzo.cambiazo.donations.domain.exceptions.CategoryOngNotFoundException;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.commands.CreateOngCommand;
import com.techzo.cambiazo.donations.domain.model.commands.UpdateOngCommand;
import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.CategoryOngRepository;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.OngRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OngCommandServiceImplTest {

    @Mock
    private OngRepository ongRepository;

    @Mock
    private CategoryOngRepository categoryOngRepository;

    @InjectMocks
    private OngCommandServiceImpl ongCommandService;

    public OngCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldCreateOng_WhenValidCommand() {
        var command = new CreateOngCommand("OngName", "email@example.com", "type", "aboutUs", "mission", "support", "address", "123456789", "logo", "website", "schedule", 1L);
        var categoryOng = new CategoryOng("Education");
        var ong = new Ong(command, categoryOng);

        when(categoryOngRepository.findById(command.categoryOngId())).thenReturn(Optional.of(categoryOng));
        when(ongRepository.findByName(command.name())).thenReturn(Optional.empty());
        when(ongRepository.findByEmail(command.email())).thenReturn(Optional.empty());
        when(ongRepository.save(any(Ong.class))).thenReturn(ong);

        var result = ongCommandService.handle(command);

        assertTrue(result.isPresent());
        assertEquals(command.name(), result.get().getName());
        verify(categoryOngRepository, times(1)).findById(command.categoryOngId());
        verify(ongRepository, times(1)).findByName(command.name());
        verify(ongRepository, times(1)).findByEmail(command.email());
        verify(ongRepository, times(1)).save(any(Ong.class));
    }

    @Test
    void testShouldThrowException_WhenCategoryOngNotFound() {
        var command = new CreateOngCommand("OngName", "email@example.com", "type", "aboutUs", "mission", "support", "address", "123456789", "logo", "website", "schedule", 1L);

        when(categoryOngRepository.findById(command.categoryOngId())).thenReturn(Optional.empty());

        var exception = assertThrows(CategoryOngNotFoundException.class, () -> ongCommandService.handle(command));
        assertEquals("Category Ong with id 1 not found", exception.getMessage()); // Cambiado "ID" por "id"
        verify(categoryOngRepository, times(1)).findById(command.categoryOngId());
    }

    @Test
    void testShouldThrowException_WhenOngNameAlreadyExists() {
        var command = new CreateOngCommand("OngName", "email@example.com", "type", "aboutUs", "mission", "support", "address", "123456789", "logo", "website", "schedule", 1L);
        var existingOng = new Ong();

        when(categoryOngRepository.findById(command.categoryOngId())).thenReturn(Optional.of(new CategoryOng("Education")));
        when(ongRepository.findByName(command.name())).thenReturn(Optional.of(existingOng));

        var exception = assertThrows(IllegalArgumentException.class, () -> ongCommandService.handle(command));
        assertEquals("Ong with name already exists", exception.getMessage());
        verify(ongRepository, times(1)).findByName(command.name());
    }

    @Test
    void testShouldUpdateOng_WhenValidCommand() {
        var command = new UpdateOngCommand(1L, "UpdatedName", "type", "aboutUs", "mission", "support", "address", "email@example.com", "123456789", "logo", "website", 1L, "schedule");
        var categoryOng = new CategoryOng("Education");
        var existingOng = new Ong();

        when(ongRepository.findById(command.id())).thenReturn(Optional.of(existingOng));
        when(categoryOngRepository.findById(command.categoryOngId())).thenReturn(Optional.of(categoryOng));
        when(ongRepository.save(any(Ong.class))).thenReturn(existingOng);

        var result = ongCommandService.handle(command);

        assertTrue(result.isPresent());
        assertEquals(command.name(), result.get().getName());
        verify(ongRepository, times(1)).findById(command.id());
        verify(categoryOngRepository, times(1)).findById(command.categoryOngId());
        verify(ongRepository, times(1)).save(any(Ong.class));
    }

    @Test
    void testHandleDeleteOng_ShouldReturnTrue_WhenOngExists() {
        var ong = new Ong();

        when(ongRepository.findById(1L)).thenReturn(Optional.of(ong));

        var result = ongCommandService.handleDeleteOng(1L);

        assertTrue(result);
        verify(ongRepository, times(1)).findById(1L);
        verify(ongRepository, times(1)).delete(ong);
    }

    @Test
    void testHandleDeleteOng_ShouldReturnFalse_WhenOngDoesNotExist() {
        when(ongRepository.findById(1L)).thenReturn(Optional.empty());

        var result = ongCommandService.handleDeleteOng(1L);

        assertFalse(result);
        verify(ongRepository, times(1)).findById(1L);
        verify(ongRepository, never()).delete(any(Ong.class));
    }
}