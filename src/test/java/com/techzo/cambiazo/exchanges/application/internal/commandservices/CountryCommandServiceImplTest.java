package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateCountryCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Country;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.ICountryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryCommandServiceImplTest {

    @Mock
    private ICountryRepository countryRepository;

    @InjectMocks
    private CountryCommandServiceImpl countryCommandService;

    public CountryCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_ShouldCreateCountry_WhenValidCommand() {
        // Arrange
        var command = new CreateCountryCommand("Argentina");
        var country = new Country(command);

        when(countryRepository.existsByName(command.name())).thenReturn(false);
        when(countryRepository.save(any(Country.class))).thenReturn(country);

        // Act
        var result = countryCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(command.name(), result.get().getName());
        verify(countryRepository, times(1)).existsByName(command.name());
        verify(countryRepository, times(1)).save(any(Country.class));
    }

    @Test
    void handle_ShouldThrowException_WhenCountryAlreadyExists() {
        // Arrange
        var command = new CreateCountryCommand("Argentina");

        when(countryRepository.existsByName(command.name())).thenReturn(true);

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> countryCommandService.handle(command));
        assertEquals("Country with same name already exists", exception.getMessage());
        verify(countryRepository, times(1)).existsByName(command.name());
        verify(countryRepository, never()).save(any(Country.class));
    }
}