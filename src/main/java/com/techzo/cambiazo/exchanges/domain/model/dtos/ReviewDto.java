package com.techzo.cambiazo.exchanges.domain.model.dtos;

import com.techzo.cambiazo.iam.interfaces.rest.resources.UserResource2;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String message;
    private Integer rating;
    private String state;
    private Long exchangeId;
    private UserResource2 userAuthor;
    private UserResource2 userReceptor;
}
