package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.domain.model.commands.CreateReviewCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.domain.model.entities.Review;
import com.techzo.cambiazo.exchanges.domain.model.entities.User;
import com.techzo.cambiazo.exchanges.domain.services.IReviewCommandService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IExchangeRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IReviewRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewCommandServiceImpl implements IReviewCommandService {

    private final IReviewRepository reviewRepository;

    private final IUserRepository userRepository;

    private final IExchangeRepository exchangeRepository;

    public ReviewCommandServiceImpl(IReviewRepository reviewRepository, IUserRepository userRepository, IExchangeRepository exchangeRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.exchangeRepository = exchangeRepository;
    }

    @Override
    public Optional<Review>handle(CreateReviewCommand command){
        Exchange exchange = exchangeRepository.findById(command.exchangeId()).orElseThrow(() -> new IllegalArgumentException("Exchange not found"));
        User userAuthor = userRepository.findById(command.userAuthorId()).orElseThrow(() -> new IllegalArgumentException("User author not found"));
        User userReceptor = userRepository.findById(command.userReceptorId()).orElseThrow(() -> new IllegalArgumentException("User receptor not found"));
        var review = new Review(command, exchange, userAuthor, userReceptor);
        var createdReview = reviewRepository.save(review);
        return Optional.of(createdReview);
    }

    @Override
    public boolean handleDeleteReview(Long id) {
        Optional<Review>review= reviewRepository.findById(id);
        if(review.isPresent()){
            reviewRepository.delete(review.get());
            return true;
        }else {
            throw new IllegalArgumentException("Review not found");
        }
    }
}
