package com.techzo.cambiazo.exchanges.domain.services;

import com.techzo.cambiazo.exchanges.domain.model.entities.User;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllUsersQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

public interface IUserQueryService {

    Optional<User>handle(GetUserByIdQuery query);

    List<User>handle(GetAllUsersQuery query);
}
