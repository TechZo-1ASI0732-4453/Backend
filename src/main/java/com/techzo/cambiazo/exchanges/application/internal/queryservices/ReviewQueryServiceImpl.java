package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.Review;
import com.techzo.cambiazo.exchanges.domain.model.entities.User;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllReviewsByUserReceptorIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IReviewQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IReviewRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewQueryServiceImpl implements IReviewQueryService {

    private final IReviewRepository reviewRepository;

    private final IUserRepository userRepository;

    public ReviewQueryServiceImpl(IReviewRepository reviewRepository, IUserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Review> handle(GetAllReviewsByUserReceptorIdQuery query) {
        User user = this.userRepository.findById(query.userReceptorId())
                .orElseThrow(()->new IllegalArgumentException("User not found"));
        return this.reviewRepository.findReviewsByUserReceptorId(user);
    }
}
