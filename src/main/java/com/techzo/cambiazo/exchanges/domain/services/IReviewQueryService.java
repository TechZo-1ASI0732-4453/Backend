package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.Review;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllReviewsByUserReceptorIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAverageRatingUserQuery;

import java.util.List;

public interface IReviewQueryService {

    List<Review>handle(GetAllReviewsByUserReceptorIdQuery query);

    Double getAverageRatingByUserReceptorId(GetAverageRatingUserQuery query);
}
