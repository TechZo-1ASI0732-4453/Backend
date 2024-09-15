package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.entities.User;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllUsersQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetUserByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IUserQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserQueryServiceImpl implements IUserQueryService {

    private final IUserRepository userRepository;

    public UserQueryServiceImpl(IUserRepository userRepository){
        this.userRepository= userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return this.userRepository.findById(query.id());
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return this.userRepository.findAll();
    }
}
