package com.techzo.cambiazo.iam.application.internal;

import com.techzo.cambiazo.iam.application.internal.commandservices.RoleCommandServiceImpl;
import com.techzo.cambiazo.iam.domain.model.commands.SeedRolesCommand;
import com.techzo.cambiazo.iam.domain.model.entities.Role;
import com.techzo.cambiazo.iam.domain.model.valueobjects.Roles;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class RoleCommandServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleCommandServiceImpl roleCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldSeedRoles_WhenRolesDoNotExist() {
        // Arrange
        var command = new SeedRolesCommand();
        for (Roles role : Roles.values()) {
            when(roleRepository.existsByName(role)).thenReturn(false);
        }

        // Act
        roleCommandService.handle(command);

        // Assert
        for (Roles role : Roles.values()) {
            verify(roleRepository, times(1)).existsByName(role);
            verify(roleRepository, times(1)).save(new Role(role));
        }
    }

    @Test
    void testShouldNotSeedRoles_WhenRolesAlreadyExist() {
        // Arrange
        var command = new SeedRolesCommand();
        for (Roles role : Roles.values()) {
            when(roleRepository.existsByName(role)).thenReturn(true);
        }

        // Act
        roleCommandService.handle(command);

        // Assert
        for (Roles role : Roles.values()) {
            verify(roleRepository, times(1)).existsByName(role);
            verify(roleRepository, never()).save(new Role(role));
        }
    }
}