package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.Review;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllReviewsByUserReceptorIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAverageRatingUserQuery;
import com.techzo.cambiazo.exchanges.domain.services.IReviewQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IReviewRepository;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewQueryServiceImpl implements IReviewQueryService {

    private final IReviewRepository reviewRepository;

    private final UserRepository userRepository;

    public ReviewQueryServiceImpl(IReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Review> handle(GetAllReviewsByUserReceptorIdQuery query) {
        User user = this.userRepository.findById(query.userReceptorId())
                .orElseThrow(()->new IllegalArgumentException("User not found"));
        return this.reviewRepository.findReviewsByUserReceptorId(user);
    }


    @Override
    public Double getAverageRatingByUserReceptorId(GetAverageRatingUserQuery query) {
        User user = this.userRepository.findById(query.userId())
                .orElseThrow(()->new IllegalArgumentException("User not found"));
        List<Review> reviews = this.reviewRepository.findReviewsByUserReceptorId(user);

        if(reviews.isEmpty()){
            return 0.0;
        }else {
            return reviews.stream().mapToDouble(Review::getRating).average().getAsDouble();
        }
    }

}
