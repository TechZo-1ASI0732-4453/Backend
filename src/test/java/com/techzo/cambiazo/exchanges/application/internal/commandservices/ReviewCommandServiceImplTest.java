package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateReviewCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Review;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IExchangeRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IReviewRepository;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewCommandServiceImplTest {

    @Mock
    private IReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IExchangeRepository exchangeRepository;

    @InjectMocks
    private ReviewCommandServiceImpl reviewCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldCreateReview_WhenValidCommand() {
        // Arrange
        var command = new CreateReviewCommand("Great product!", 5, "ACTIVE", 1L, 2L, 3L);
        var exchange = new Exchange();
        var userAuthor = new User();
        var userReceptor = new User();

        when(exchangeRepository.findById(command.exchangeId())).thenReturn(Optional.of(exchange));
        when(userRepository.findById(command.userAuthorId())).thenReturn(Optional.of(userAuthor));
        when(userRepository.findById(command.userReceptorId())).thenReturn(Optional.of(userReceptor));
        when(reviewRepository.findReviewByUserAuthorIdAndExchangeId(userAuthor, exchange)).thenReturn(null);
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        var result = reviewCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(command.message(), result.get().getMessage());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testShouldThrowException_WhenExchangeNotFound() {
        // Arrange
        var command = new CreateReviewCommand("Great product!", 5, "ACTIVE", 1L, 2L, 3L);
        when(exchangeRepository.findById(command.exchangeId())).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> reviewCommandService.handle(command));
        assertEquals("Exchange not found", exception.getMessage());
        verify(exchangeRepository, times(1)).findById(command.exchangeId());
    }

    @Test
    void testShouldThrowException_WhenUserAuthorNotFound() {
        // Arrange
        var command = new CreateReviewCommand("Great product!", 5, "ACTIVE", 1L, 2L, 3L);
        var exchange = new Exchange();

        when(exchangeRepository.findById(command.exchangeId())).thenReturn(Optional.of(exchange));
        when(userRepository.findById(command.userAuthorId())).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> reviewCommandService.handle(command));
        assertEquals("User author not found", exception.getMessage());
        verify(userRepository, times(1)).findById(command.userAuthorId());
    }

    @Test
    void testShouldThrowException_WhenReviewAlreadyExists() {
        // Arrange
        var command = new CreateReviewCommand("Great product!", 5, "ACTIVE", 1L, 2L, 3L);
        var exchange = new Exchange();
        var userAuthor = new User();
        var userReceptor = new User(); // Agregar el receptor
        var existingReview = new Review();

        when(exchangeRepository.findById(command.exchangeId())).thenReturn(Optional.of(exchange));
        when(userRepository.findById(command.userAuthorId())).thenReturn(Optional.of(userAuthor));
        when(userRepository.findById(command.userReceptorId())).thenReturn(Optional.of(userReceptor)); // Simular el receptor
        when(reviewRepository.findReviewByUserAuthorIdAndExchangeId(userAuthor, exchange)).thenReturn(existingReview);

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> reviewCommandService.handle(command));
        assertEquals("Review already exists", exception.getMessage());
        verify(reviewRepository, times(1)).findReviewByUserAuthorIdAndExchangeId(userAuthor, exchange);
    }

    @Test
    void testDeleteReview_ShouldDeleteReview_WhenReviewExists() {
        // Arrange
        var reviewId = 1L;
        var review = new Review();

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        // Act
        var result = reviewCommandService.handleDeleteReview(reviewId);

        // Assert
        assertTrue(result);
        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    void testDeleteReview_ShouldThrowException_WhenReviewNotFound() {
        // Arrange
        var reviewId = 1L;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> reviewCommandService.handleDeleteReview(reviewId));
        assertEquals("Review not found", exception.getMessage());
        verify(reviewRepository, times(1)).findById(reviewId);
    }
}