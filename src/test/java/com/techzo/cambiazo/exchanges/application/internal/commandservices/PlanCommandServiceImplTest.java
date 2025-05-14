package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreatePlanCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Plan;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IPlanRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanCommandServiceImplTest {

    @Mock
    private IPlanRepository planRepository;

    @InjectMocks
    private PlanCommandServiceImpl planCommandService;

    public PlanCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_ShouldCreatePlan_WhenValidCommand() {
        // Arrange
        var command = new CreatePlanCommand("Plan Básico", "Descripción del plan básico", 19.99);
        when(planRepository.existsByName(command.name())).thenReturn(false);
        var plan = new Plan(command);
        when(planRepository.save(any(Plan.class))).thenReturn(plan);

        // Act
        var result = planCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(command.name(), result.get().getName());
        assertEquals(command.description(), result.get().getDescription());
        assertEquals(command.price(), result.get().getPrice());
        verify(planRepository, times(1)).existsByName(command.name());
        verify(planRepository, times(1)).save(any(Plan.class));
    }

    @Test
    void handle_ShouldThrowException_WhenPlanWithSameNameExists() {
        // Arrange
        var command = new CreatePlanCommand("Plan Básico", "Descripción del plan básico", 19.99);
        when(planRepository.existsByName(command.name())).thenReturn(true);

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> planCommandService.handle(command));
        assertEquals("Plan with same name already exists", exception.getMessage());
        verify(planRepository, times(1)).existsByName(command.name());
        verify(planRepository, never()).save(any(Plan.class));
    }
}