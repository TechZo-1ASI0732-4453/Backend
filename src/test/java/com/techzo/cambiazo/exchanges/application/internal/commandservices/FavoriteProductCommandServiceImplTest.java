package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateFavoriteProductCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.FavoriteProduct;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IFavoriteProductRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductRepository;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavoriteProductCommandServiceImplTest {

    @Mock
    private IFavoriteProductRepository favoriteProductRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private FavoriteProductCommandServiceImpl favoriteProductCommandService;

    public FavoriteProductCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void handle_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        var command = new CreateFavoriteProductCommand(1L, 2L);
        when(userRepository.findById(command.userId())).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> favoriteProductCommandService.handle(command));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(command.userId());
        verify(productRepository, never()).findById(anyLong());
    }

    @Test
    void handle_ShouldThrowException_WhenProductNotFound() {
        // Arrange
        var command = new CreateFavoriteProductCommand(1L, 2L);
        var user = new User();
        when(userRepository.findById(command.userId())).thenReturn(Optional.of(user));
        when(productRepository.findById(command.productId())).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> favoriteProductCommandService.handle(command));
        assertEquals("Product not found", exception.getMessage());
        verify(userRepository, times(1)).findById(command.userId());
        verify(productRepository, times(1)).findById(command.productId());
    }

    @Test
    void handle_ShouldThrowException_WhenFavoriteProductAlreadyExists() {
        // Arrange
        var command = new CreateFavoriteProductCommand(1L, 2L);
        var user = new User();
        var product = new Product();
        when(userRepository.findById(command.userId())).thenReturn(Optional.of(user));
        when(productRepository.findById(command.productId())).thenReturn(Optional.of(product));
        when(favoriteProductRepository.existsByUserIdAndProductId(user, product)).thenReturn(true);

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> favoriteProductCommandService.handle(command));
        assertEquals("Favorite Product already exists", exception.getMessage());
        verify(favoriteProductRepository, never()).save(any(FavoriteProduct.class));
    }

    @Test
    void handleDeleteFavoriteProductByUserIdAndProductId_ShouldDelete_WhenExists() {
        // Arrange
        var user = new User();
        var product = new Product();
        var favoriteProduct = new FavoriteProduct(product, user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));
        when(favoriteProductRepository.findFavoriteProductByUserIdAndProductId(user, product)).thenReturn(Optional.of(favoriteProduct));

        // Act
        var result = favoriteProductCommandService.handleDeleteFavoriteProductByUserIdAndProductId(1L, 2L);

        // Assert
        assertTrue(result);
        verify(favoriteProductRepository, times(1)).delete(favoriteProduct);
    }

    @Test
    void handleDeleteFavoriteProductByUserIdAndProductId_ShouldReturnFalse_WhenNotExists() {
        // Arrange
        var user = new User();
        var product = new Product();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));
        when(favoriteProductRepository.findFavoriteProductByUserIdAndProductId(user, product)).thenReturn(Optional.empty());

        // Act
        var result = favoriteProductCommandService.handleDeleteFavoriteProductByUserIdAndProductId(1L, 2L);

        // Assert
        assertFalse(result);
        verify(favoriteProductRepository, never()).delete(any(FavoriteProduct.class));
    }

    @Test
    void handleDeleteFavoriteProductById_ShouldDelete_WhenExists() {
        // Arrange
        when(favoriteProductRepository.existsFavoriteProductById(1L)).thenReturn(true);

        // Act
        var result = favoriteProductCommandService.handleDeleteFavoriteProductById(1L);

        // Assert
        assertTrue(result);
        verify(favoriteProductRepository, times(1)).deleteById(1L);
    }

    @Test
    void handleDeleteFavoriteProductById_ShouldReturnFalse_WhenNotExists() {
        // Arrange
        when(favoriteProductRepository.existsFavoriteProductById(1L)).thenReturn(false);

        // Act
        var result = favoriteProductCommandService.handleDeleteFavoriteProductById(1L);

        // Assert
        assertFalse(result);
        verify(favoriteProductRepository, never()).deleteById(anyLong());
    }
}