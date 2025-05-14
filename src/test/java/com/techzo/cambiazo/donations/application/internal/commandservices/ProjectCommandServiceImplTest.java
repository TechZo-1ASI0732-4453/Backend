package com.techzo.cambiazo.donations.application.internal.commandservices;

import com.techzo.cambiazo.donations.domain.exceptions.OngNotFoundException;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.aggregates.Project;
import com.techzo.cambiazo.donations.domain.model.commands.CreateProjectCommand;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.OngRepository;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectCommandServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private OngRepository ongRepository;

    @InjectMocks
    private ProjectCommandServiceImpl projectCommandService;

    public ProjectCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_ShouldCreateProject_WhenValidCommand() {
        var command = new CreateProjectCommand("ProjectName", "ProjectDescription", 1L);
        var ong = new Ong();
        var project = new Project(command, ong);

        when(ongRepository.findById(command.ongId())).thenReturn(Optional.of(ong));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        var result = projectCommandService.handle(command);

        assertTrue(result.isPresent());
        assertEquals(command.name(), result.get().getName());
        assertEquals(command.description(), result.get().getDescription());
        verify(ongRepository, times(1)).findById(command.ongId());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void handle_ShouldThrowException_WhenOngNotFound() {
        var command = new CreateProjectCommand("ProjectName", "ProjectDescription", 1L);

        when(ongRepository.findById(command.ongId())).thenReturn(Optional.empty());

        var exception = assertThrows(OngNotFoundException.class, () -> projectCommandService.handle(command));
        assertEquals("Ong with id 1 not found", exception.getMessage()); // Cambiado "ID" por "id"
        verify(ongRepository, times(1)).findById(command.ongId());
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void handleDeleteProject_ShouldReturnTrue_WhenProjectExists() {
        var project = new Project();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        var result = projectCommandService.handleDeleteProject(1L);

        assertTrue(result);
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).delete(project);
    }

    @Test
    void handleDeleteProject_ShouldReturnFalse_WhenProjectDoesNotExist() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        var result = projectCommandService.handleDeleteProject(1L);

        assertFalse(result);
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, never()).delete(any(Project.class));
    }
}