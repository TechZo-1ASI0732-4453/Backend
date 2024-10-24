package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.dtos.AverageAndCountReviewsDto;
import com.techzo.cambiazo.exchanges.domain.model.dtos.ExistReview;
import com.techzo.cambiazo.exchanges.domain.model.dtos.ReviewDto;
import com.techzo.cambiazo.exchanges.domain.model.entities.Review;
import com.techzo.cambiazo.exchanges.domain.model.queries.FindReviewByUserAuthorIdAndExchangeId;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllReviewsByUserReceptorIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAverageRatingAndCountReviewsUserQuery;

import java.util.List;

public interface IReviewQueryService {

    List<ReviewDto>handle(GetAllReviewsByUserReceptorIdQuery query);
    ExistReview existsByUserAuthorIdAndExchangeId(FindReviewByUserAuthorIdAndExchangeId query);
    AverageAndCountReviewsDto getAverageRatingAndCountReviewsByUserReceptorId(GetAverageRatingAndCountReviewsUserQuery query);
}
