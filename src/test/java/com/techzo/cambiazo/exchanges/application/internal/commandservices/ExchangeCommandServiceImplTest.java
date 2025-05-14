package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateExchangeCommand;
import com.techzo.cambiazo.exchanges.domain.model.commands.UpdateExchangeStatusCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IExchangeRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExchangeCommandServiceImplTest {

    @Mock
    private IExchangeRepository exchangeRepository;

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ExchangeCommandServiceImpl exchangeCommandService;

    public ExchangeCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_ShouldCreateExchange_WhenValidCommand() {
        // Arrange
        var command = new CreateExchangeCommand(1L, 2L, "Pendiente");
        var productOwn = new Product();
        var productChange = new Product();
        when(productRepository.findById(command.productOwnId())).thenReturn(Optional.of(productOwn));
        when(productRepository.findById(command.productChangeId())).thenReturn(Optional.of(productChange));
        when(exchangeRepository.findExchangeByProductOwnIdAndProductChangeId(productOwn, productChange)).thenReturn(null);
        var exchange = new Exchange(command, productOwn, productChange);
        when(exchangeRepository.save(any(Exchange.class))).thenReturn(exchange);

        // Act
        var result = exchangeCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(command.status(), result.get().getStatus());
        verify(productRepository, times(1)).findById(command.productOwnId());
        verify(productRepository, times(1)).findById(command.productChangeId());
        verify(exchangeRepository, times(1)).save(any(Exchange.class));
    }

    @Test
    void handle_ShouldThrowException_WhenProductOwnNotFound() {
        // Arrange
        var command = new CreateExchangeCommand(1L, 2L, "Pendiente");
        when(productRepository.findById(command.productOwnId())).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> exchangeCommandService.handle(command));
        assertEquals("Product Own not found", exception.getMessage());
        verify(productRepository, times(1)).findById(command.productOwnId());
        verify(productRepository, never()).findById(command.productChangeId());
    }

    @Test
    void handle_ShouldThrowException_WhenExchangeAlreadyExists() {
        // Arrange
        var command = new CreateExchangeCommand(1L, 2L, "Pendiente");
        var productOwn = new Product();
        var productChange = new Product();
        when(productRepository.findById(command.productOwnId())).thenReturn(Optional.of(productOwn));
        when(productRepository.findById(command.productChangeId())).thenReturn(Optional.of(productChange));
        when(exchangeRepository.findExchangeByProductOwnIdAndProductChangeId(productOwn, productChange)).thenReturn(new Exchange());

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> exchangeCommandService.handle(command));
        assertEquals("Exchange already exists", exception.getMessage());
        verify(exchangeRepository, never()).save(any(Exchange.class));
    }

    @Test
    void handleDeleteExchange_ShouldDeleteExchange_WhenExchangeExistsAndPending() {
        // Arrange
        var exchange = new Exchange();
        exchange.setStatus("Pendiente");
        when(exchangeRepository.findById(1L)).thenReturn(Optional.of(exchange));

        // Act
        var result = exchangeCommandService.handleDeleteExchange(1L);

        // Assert
        assertTrue(result);
        verify(exchangeRepository, times(1)).delete(exchange);
    }

    @Test
    void handleDeleteExchange_ShouldThrowException_WhenExchangeNotFound() {
        // Arrange
        when(exchangeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> exchangeCommandService.handleDeleteExchange(1L));
        assertEquals("Exchange not found", exception.getMessage());
    }
}