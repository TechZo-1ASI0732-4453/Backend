package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.entities.Exchange;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ExchangeResource;

public class ExchangeResourceFromEntityAssembler {
        public static ExchangeResource toResourceFromEntity(Exchange entity) {
            return new ExchangeResource(entity.getId(), entity.getProductOwnId(), entity.getProductChangeId(), entity.getStatus());
        }
}
