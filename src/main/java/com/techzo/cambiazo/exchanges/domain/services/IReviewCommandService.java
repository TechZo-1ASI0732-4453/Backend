package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateReviewCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Review;

import java.util.Optional;

public interface IReviewCommandService {
    Optional<Review>handle(CreateReviewCommand command);

    boolean handleDeleteReview(Long id);
}
