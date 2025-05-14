package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateBenefitCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Benefit;
import com.techzo.cambiazo.exchanges.domain.model.entities.Plan;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IBenefitRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IPlanRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BenefitCommandServiceImplTest {

    @Mock
    private IBenefitRepository benefitRepository;

    @Mock
    private IPlanRepository planRepository;

    @InjectMocks
    private BenefitCommandServiceImpl benefitCommandService;

    public BenefitCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldCreateBenefit_WhenValidCommand() {
        // Arrange
        var command = new CreateBenefitCommand("Free Shipping", 1L);
        var plan = new Plan();
        plan.setId(1L);
        var benefit = new Benefit(command, plan);

        when(planRepository.findById(command.planId())).thenReturn(Optional.of(plan));
        when(benefitRepository.save(any(Benefit.class))).thenReturn(benefit);

        // Act
        var result = benefitCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(command.description(), result.get().getDescription());
        assertEquals(command.planId(), result.get().getPlanId());
        verify(planRepository, times(1)).findById(command.planId());
        verify(benefitRepository, times(1)).save(any(Benefit.class));
    }

    @Test
    void testShouldThrowException_WhenPlanNotFound() {
        // Arrange
        var command = new CreateBenefitCommand("Free Shipping", 1L);

        when(planRepository.findById(command.planId())).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> benefitCommandService.handle(command));
        assertEquals("Plan with same id already exists", exception.getMessage());
        verify(planRepository, times(1)).findById(command.planId());
        verify(benefitRepository, never()).save(any(Benefit.class));
    }
}