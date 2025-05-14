package com.techzo.cambiazo.iam.application.internal.commandservices;

import com.techzo.cambiazo.iam.application.internal.outboundservices.hashing.HashingService;
import com.techzo.cambiazo.iam.application.internal.outboundservices.tokens.TokenService;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.domain.model.commands.SignInCommand;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCommandServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HashingService hashingService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserCommandServiceImpl userCommandService;

    public UserCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_ShouldReturnUserAndToken_WhenValidSignInCommand() {
        // Arrange
        var username = "testuser";
        var password = "password123";
        var hashedPassword = "hashedPassword123";
        var token = "generatedToken";

        var user = new User(username, hashedPassword, "Test User", "123456789", null, false, null);
        var command = new SignInCommand(username, password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(hashingService.matches(password, hashedPassword)).thenReturn(true);
        when(tokenService.generateToken(username)).thenReturn(token);

        // Act
        var result = userCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getLeft().getUsername());
        assertEquals(token, result.get().getRight());
        verify(userRepository, times(1)).findByUsername(username);
        verify(hashingService, times(1)).matches(password, hashedPassword);
        verify(tokenService, times(1)).generateToken(username);
    }

    @Test
    void handle_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        var command = new SignInCommand("nonexistentuser", "password123");
        when(userRepository.findByUsername(command.username())).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(RuntimeException.class, () -> userCommandService.handle(command));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(command.username());
    }

    @Test
    void handle_ShouldThrowException_WhenPasswordIsInvalid() {
        // Arrange
        var username = "testuser";
        var password = "wrongPassword";
        var hashedPassword = "hashedPassword123";

        var user = new User(username, hashedPassword, "Test User", "123456789", null, false, null);
        var command = new SignInCommand(username, password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(hashingService.matches(password, hashedPassword)).thenReturn(false);

        // Act & Assert
        var exception = assertThrows(RuntimeException.class, () -> userCommandService.handle(command));
        assertEquals("Invalid password", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
        verify(hashingService, times(1)).matches(password, hashedPassword);
    }
}