package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateDistrictCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Department;
import com.techzo.cambiazo.exchanges.domain.model.entities.District;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IDepartmentRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IDistrictRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DistrictCommandServiceImplTest {

    @Mock
    private IDistrictRepository districtRepository;

    @Mock
    private IDepartmentRepository departmentRepository;

    @InjectMocks
    private DistrictCommandServiceImpl districtCommandService;

    public DistrictCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_ShouldCreateDistrict_WhenValidCommand() {
        // Arrange
        var command = new CreateDistrictCommand("Palermo", 1L);
        var department = new Department();
        department.setId(1L);
        var district = new District(command, department);

        when(departmentRepository.findById(command.departmentId())).thenReturn(Optional.of(department));
        when(districtRepository.existsByName(command.name())).thenReturn(false);
        when(districtRepository.save(any(District.class))).thenReturn(district);

        // Act
        var result = districtCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(command.name(), result.get().getName());
        assertEquals(command.departmentId(), result.get().getDepartmentId());
        verify(departmentRepository, times(1)).findById(command.departmentId());
        verify(districtRepository, times(1)).existsByName(command.name());
        verify(districtRepository, times(1)).save(any(District.class));
    }

    @Test
    void handle_ShouldThrowException_WhenDepartmentNotFound() {
        // Arrange
        var command = new CreateDistrictCommand("Palermo", 1L);

        when(departmentRepository.findById(command.departmentId())).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> districtCommandService.handle(command));
        assertEquals("Department not found", exception.getMessage());
        verify(departmentRepository, times(1)).findById(command.departmentId());
        verify(districtRepository, never()).existsByName(anyString());
        verify(districtRepository, never()).save(any(District.class));
    }

    @Test
    void handle_ShouldThrowException_WhenDistrictAlreadyExists() {
        // Arrange
        var command = new CreateDistrictCommand("Palermo", 1L);
        var department = new Department();
        department.setId(1L);

        when(departmentRepository.findById(command.departmentId())).thenReturn(Optional.of(department));
        when(districtRepository.existsByName(command.name())).thenReturn(true);

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> districtCommandService.handle(command));
        assertEquals("District with same name already exists", exception.getMessage());
        verify(departmentRepository, times(1)).findById(command.departmentId());
        verify(districtRepository, times(1)).existsByName(command.name());
        verify(districtRepository, never()).save(any(District.class));
    }
}