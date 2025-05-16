package com.techzo.cambiazo.exchanges.interfaces.rest;

import com.techzo.cambiazo.exchanges.domain.model.dtos.AverageAndCountReviewsDto;
import com.techzo.cambiazo.exchanges.domain.model.dtos.ExistReview;
import com.techzo.cambiazo.exchanges.domain.model.dtos.ReviewDto;
import com.techzo.cambiazo.exchanges.domain.model.queries.FindReviewByUserAuthorIdAndExchangeId;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllReviewsByUserReceptorIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAverageRatingAndCountReviewsUserQuery;
import com.techzo.cambiazo.exchanges.domain.services.IReviewCommandService;
import com.techzo.cambiazo.exchanges.domain.services.IReviewQueryService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateReviewResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ReviewResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateReviewCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.ReviewResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v2/reviews")
@Hidden
@Tag(name = "Reviews", description = "Reviews Management Endpoints")
public class ReviewController {

    private final IReviewCommandService reviewCommandService;

    private final IReviewQueryService reviewQueryService;

    public ReviewController(IReviewCommandService reviewCommandService, IReviewQueryService reviewQueryService) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
    }

    @Operation(summary = "Create a new Review", description = "Create a new Review with the input data.")
    @PostMapping
    public ResponseEntity<ReviewResource>createReview(@RequestBody CreateReviewResource resource) {
        try {
            var createReviewCommand = CreateReviewCommandFromResourceAssembler.toCommandFromResource(resource);
            var review = reviewCommandService.handle(createReviewCommand);
            var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(review.get());
            return ResponseEntity.status(CREATED).body(reviewResource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user-receptor/{userId}")
    public ResponseEntity<List<ReviewDto>> getAllReviewsByUserReceptorId(@PathVariable Long userId) {
        try {
            var getAllReviewsByUserReceptorIdQuery = new GetAllReviewsByUserReceptorIdQuery(userId);
            var reviews = reviewQueryService.handle(getAllReviewsByUserReceptorIdQuery);
            return ResponseEntity.ok(reviews);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        try {
            reviewCommandService.handleDeleteReview(reviewId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/average-count/{userId}")
    public ResponseEntity<AverageAndCountReviewsDto> getAverageRatingByUserReceptorId(@PathVariable Long userId) {
        try {
            var getAverageRatingUserQuery = new GetAverageRatingAndCountReviewsUserQuery(userId);
            var averageRatingAndCountReviews = reviewQueryService.getAverageRatingAndCountReviewsByUserReceptorId(getAverageRatingUserQuery);
            return ResponseEntity.ok(averageRatingAndCountReviews);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user-author/{userId}/exchange/{exchangeId}")
    public ResponseEntity<ExistReview> existsByUserAuthorIdAndExchangeId(@PathVariable Long userId, @PathVariable Long exchangeId) {
        try {
            var findReviewByUserAuthorIdAndExchangeId = new FindReviewByUserAuthorIdAndExchangeId(userId, exchangeId);
            var existsReview = reviewQueryService.existsByUserAuthorIdAndExchangeId(findReviewByUserAuthorIdAndExchangeId);
            return ResponseEntity.ok(existsReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
