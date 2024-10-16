package com.techzo.cambiazo.donations.domain.services;

import com.techzo.cambiazo.donations.domain.model.aggregates.SocialNetwork;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllSocialNetworkByOngIdQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllSocialNetworksQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetSocialNetworkByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SocialNetworkQueryService {
    Optional<SocialNetwork> handle(GetSocialNetworkByIdQuery query);
    List<SocialNetwork> handle(GetAllSocialNetworksQuery query);
    List<SocialNetwork> handle(GetAllSocialNetworkByOngIdQuery query);
}
