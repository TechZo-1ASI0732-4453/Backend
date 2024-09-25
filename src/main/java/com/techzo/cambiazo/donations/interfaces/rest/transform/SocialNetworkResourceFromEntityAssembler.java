package com.techzo.cambiazo.donations.interfaces.rest.transform;

import com.techzo.cambiazo.donations.domain.model.aggregates.SocialNetwork;
import com.techzo.cambiazo.donations.interfaces.rest.resources.SocialNetworkResource;

public class SocialNetworkResourceFromEntityAssembler {
    public static SocialNetworkResource toResourceFromEntity(SocialNetwork entity) {
        return new SocialNetworkResource(entity.getId(), entity.getName(), entity.getUrl(), entity.getOngId());
    }
}
