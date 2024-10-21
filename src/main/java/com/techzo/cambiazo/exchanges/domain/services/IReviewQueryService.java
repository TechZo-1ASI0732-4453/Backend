package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.dtos.AverageAndCountReviewsDto;
import com.techzo.cambiazo.exchanges.domain.model.dtos.ReviewDto;
import com.techzo.cambiazo.exchanges.domain.model.entities.Review;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllReviewsByUserReceptorIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAverageRatingAndCountReviewsUserQuery;

import java.util.List;

public interface IReviewQueryService {

    List<ReviewDto>handle(GetAllReviewsByUserReceptorIdQuery query);

    AverageAndCountReviewsDto getAverageRatingAndCountReviewsByUserReceptorId(GetAverageRatingAndCountReviewsUserQuery query);
}
