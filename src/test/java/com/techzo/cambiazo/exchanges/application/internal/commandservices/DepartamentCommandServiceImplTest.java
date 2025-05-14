package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateDepartmentCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Country;
import com.techzo.cambiazo.exchanges.domain.model.entities.Department;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.ICountryRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IDepartmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartamentCommandServiceImplTest {

    @Mock
    private IDepartmentRepository departmentRepository;

    @Mock
    private ICountryRepository countryRepository;

    @InjectMocks
    private DepartmentCommandServiceImpl departmentCommandService;

    public DepartamentCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_ShouldCreateDepartment_WhenValidCommand() {
        // Arrange
        var command = new CreateDepartmentCommand("Buenos Aires", 1L);
        var country = new Country();
        country.setId(1L);
        var department = new Department(command, country);

        when(countryRepository.findById(command.countryId())).thenReturn(Optional.of(country));
        when(departmentRepository.existsByName(command.name())).thenReturn(false);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        // Act
        var result = departmentCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(command.name(), result.get().getName());
        assertEquals(command.countryId(), result.get().getCountryId());
        verify(countryRepository, times(1)).findById(command.countryId());
        verify(departmentRepository, times(1)).existsByName(command.name());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void handle_ShouldThrowException_WhenCountryNotFound() {
        // Arrange
        var command = new CreateDepartmentCommand("Buenos Aires", 1L);

        when(countryRepository.findById(command.countryId())).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> departmentCommandService.handle(command));
        assertEquals("Country not found", exception.getMessage());
        verify(countryRepository, times(1)).findById(command.countryId());
        verify(departmentRepository, never()).existsByName(anyString());
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void handle_ShouldThrowException_WhenDepartmentAlreadyExists() {
        // Arrange
        var command = new CreateDepartmentCommand("Buenos Aires", 1L);
        var country = new Country();
        country.setId(1L);

        when(countryRepository.findById(command.countryId())).thenReturn(Optional.of(country));
        when(departmentRepository.existsByName(command.name())).thenReturn(true);

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> departmentCommandService.handle(command));
        assertEquals("Department with same name already exists", exception.getMessage());
        verify(countryRepository, times(1)).findById(command.countryId());
        verify(departmentRepository, times(1)).existsByName(command.name());
        verify(departmentRepository, never()).save(any(Department.class));
    }
}