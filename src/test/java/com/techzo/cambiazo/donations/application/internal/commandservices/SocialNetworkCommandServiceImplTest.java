package com.techzo.cambiazo.donations.application.internal.commandservices;

import com.techzo.cambiazo.donations.domain.exceptions.OngNotFoundException;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.aggregates.SocialNetwork;
import com.techzo.cambiazo.donations.domain.model.commands.CreateSocialNetworkCommand;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.OngRepository;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.SocialNetworkRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SocialNetworkCommandServiceImplTest {

    @Mock
    private SocialNetworkRepository socialNetworkRepository;

    @Mock
    private OngRepository ongRepository;

    @InjectMocks
    private SocialNetworkCommandServiceImpl socialNetworkCommandService;

    public SocialNetworkCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_ShouldCreateSocialNetwork_WhenValidCommand() {
        var command = new CreateSocialNetworkCommand("Facebook", "https://facebook.com/ong", 1L);
        var ong = new Ong();
        var socialNetwork = new SocialNetwork(command, ong);

        when(ongRepository.findById(command.ongId())).thenReturn(Optional.of(ong));
        when(socialNetworkRepository.findByNameAndUrl(any(), any())).thenReturn(Optional.empty());
        when(socialNetworkRepository.save(any(SocialNetwork.class))).thenReturn(socialNetwork);

        var result = socialNetworkCommandService.handle(command);

        assertTrue(result.isPresent());
        assertEquals(command.name(), result.get().getName());
        assertEquals(command.url(), result.get().getUrl());
        verify(ongRepository, times(1)).findById(command.ongId());
        verify(socialNetworkRepository, times(1)).findByNameAndUrl(any(), any());
        verify(socialNetworkRepository, times(1)).save(any(SocialNetwork.class));
    }

    @Test
    void handle_ShouldThrowException_WhenOngNotFound() {
        var command = new CreateSocialNetworkCommand("Facebook", "https://facebook.com/ong", 1L);

        when(ongRepository.findById(command.ongId())).thenReturn(Optional.empty());

        var exception = assertThrows(OngNotFoundException.class, () -> socialNetworkCommandService.handle(command));
        assertEquals("Ong with id 1 not found", exception.getMessage());
        verify(ongRepository, times(1)).findById(command.ongId());
        verify(socialNetworkRepository, never()).save(any(SocialNetwork.class));
    }

    @Test
    void handle_ShouldThrowException_WhenSocialNetworkAlreadyExists() {
        var command = new CreateSocialNetworkCommand("Facebook", "https://facebook.com/ong", 1L);
        var ong = new Ong();
        var existingSocialNetwork = new SocialNetwork(command, ong);

        when(ongRepository.findById(command.ongId())).thenReturn(Optional.of(ong));
        when(socialNetworkRepository.findByNameAndUrl(any(), any())).thenReturn(Optional.of(existingSocialNetwork));

        var exception = assertThrows(IllegalArgumentException.class, () -> socialNetworkCommandService.handle(command));
        assertEquals("Social Network with name and url already exists", exception.getMessage());
        verify(ongRepository, times(1)).findById(command.ongId());
        verify(socialNetworkRepository, times(1)).findByNameAndUrl(any(), any());
        verify(socialNetworkRepository, never()).save(any(SocialNetwork.class));
    }

    @Test
    void handleDeleteSocialNetwork_ShouldReturnTrue_WhenSocialNetworkExists() {
        var socialNetwork = new SocialNetwork();

        when(socialNetworkRepository.findById(1L)).thenReturn(Optional.of(socialNetwork));

        var result = socialNetworkCommandService.handleDeleteSocialNetwork(1L);

        assertTrue(result);
        verify(socialNetworkRepository, times(1)).findById(1L);
        verify(socialNetworkRepository, times(1)).delete(socialNetwork);
    }

    @Test
    void handleDeleteSocialNetwork_ShouldReturnFalse_WhenSocialNetworkDoesNotExist() {
        when(socialNetworkRepository.findById(1L)).thenReturn(Optional.empty());

        var result = socialNetworkCommandService.handleDeleteSocialNetwork(1L);

        assertFalse(result);
        verify(socialNetworkRepository, times(1)).findById(1L);
        verify(socialNetworkRepository, never()).delete(any(SocialNetwork.class));
    }
}