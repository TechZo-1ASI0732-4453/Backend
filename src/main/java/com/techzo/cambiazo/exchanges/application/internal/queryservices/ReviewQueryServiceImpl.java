package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.dtos.AverageAndCountReviewsDto;
import com.techzo.cambiazo.exchanges.domain.model.dtos.ReviewDto;
import com.techzo.cambiazo.exchanges.domain.model.entities.Review;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllReviewsByUserReceptorIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAverageRatingAndCountReviewsUserQuery;
import com.techzo.cambiazo.exchanges.domain.services.IReviewQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IReviewRepository;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.techzo.cambiazo.iam.interfaces.rest.transform.UserResource2FromEntityAssembler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ReviewQueryServiceImpl implements IReviewQueryService {

    private final IReviewRepository reviewRepository;

    private final UserRepository userRepository;

    public ReviewQueryServiceImpl(IReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<ReviewDto> handle(GetAllReviewsByUserReceptorIdQuery query) {
        User userReceptor = this.userRepository.findById(query.userReceptorId())
                .orElseThrow(()->new IllegalArgumentException("User not found"));
        List<Review> reviews = this.reviewRepository.findReviewsByUserReceptorId(userReceptor);
        return reviews.stream().map(review -> {
            User userAuthor = this.userRepository.findById(review.getUserAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            var userAuthorResource2 = UserResource2FromEntityAssembler.toResourceFromEntity(userAuthor);
            var userReceptorResource2 = UserResource2FromEntityAssembler.toResourceFromEntity(userReceptor);
            return new ReviewDto(
                    review.getMessage(),
                    review.getRating(),
                    review.getState(),
                    review.getExchangeId(),
                    userAuthorResource2,
                    userReceptorResource2
            );
        }).collect(Collectors.toList());
    }


    @Override
    public AverageAndCountReviewsDto getAverageRatingAndCountReviewsByUserReceptorId(GetAverageRatingAndCountReviewsUserQuery query) {
        User user = this.userRepository.findById(query.userId())
                .orElseThrow(()->new IllegalArgumentException("User not found"));
        List<Review> reviews = this.reviewRepository.findReviewsByUserReceptorId(user);


        if(reviews.isEmpty()){
            return new AverageAndCountReviewsDto(0.0,0L);
        }else {
            return new AverageAndCountReviewsDto(reviews.stream().mapToDouble(Review::getRating).average().getAsDouble(),(long)reviews.size());
        }
    }

}
