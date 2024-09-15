package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.UpdateSubscriptionCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.UpdateSubscriptionResource;

public class UpdateSubscriptionCommandFromResourceAssembler {
    public static UpdateSubscriptionCommand toCommandFromResource(Long subscriptionId,UpdateSubscriptionResource resource) {
        return new UpdateSubscriptionCommand(subscriptionId, resource.state(), resource.planId(), resource.userId());
    }
}
