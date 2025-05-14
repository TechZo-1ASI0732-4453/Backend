package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateProductCategoryCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.ProductCategory;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductCategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductCategoryCommandServiceImplTest {

    @Mock
    private IProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductCategoryCommandServiceImpl productCategoryCommandService;

    public ProductCategoryCommandServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldCreateProductCategory_WhenValidCommand() {
        // Arrange
        var command = new CreateProductCategoryCommand("Electronics");
        when(productCategoryRepository.existsByName(command.name())).thenReturn(false);
        var productCategory = new ProductCategory(command);
        when(productCategoryRepository.save(any(ProductCategory.class))).thenReturn(productCategory);

        // Act
        var result = productCategoryCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(command.name(), result.get().getName());
        verify(productCategoryRepository, times(1)).existsByName(command.name());
        verify(productCategoryRepository, times(1)).save(any(ProductCategory.class));
    }

    @Test
    void testShouldThrowException_WhenProductCategoryWithSameNameExists() {
        // Arrange
        var command = new CreateProductCategoryCommand("Electronics");
        when(productCategoryRepository.existsByName(command.name())).thenReturn(true);

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> productCategoryCommandService.handle(command));
        assertEquals("Product category with same name already exists", exception.getMessage());
        verify(productCategoryRepository, times(1)).existsByName(command.name());
        verify(productCategoryRepository, never()).save(any(ProductCategory.class));
    }
}