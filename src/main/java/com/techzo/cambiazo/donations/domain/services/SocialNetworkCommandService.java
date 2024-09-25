package com.techzo.cambiazo.donations.domain.services;

import com.techzo.cambiazo.donations.domain.model.aggregates.SocialNetwork;
import com.techzo.cambiazo.donations.domain.model.commands.CreateSocialNetworkCommand;

import java.util.Optional;

public interface SocialNetworkCommandService {

    Optional<SocialNetwork> handle(CreateSocialNetworkCommand command);
    boolean handleDeleteSocialNetwork(Long id);
}
