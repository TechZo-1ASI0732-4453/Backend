package com.techzo.cambiazo.iam.domain.services;

import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.domain.model.queries.GetAllUsersQuery;
import com.techzo.cambiazo.iam.domain.model.queries.GetUserByIdQuery;
import com.techzo.cambiazo.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);

}
