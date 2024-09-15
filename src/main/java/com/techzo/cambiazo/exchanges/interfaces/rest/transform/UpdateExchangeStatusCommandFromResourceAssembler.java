package com.techzo.cambiazo.exchanges.interfaces.rest.transform;

import com.techzo.cambiazo.exchanges.domain.model.commands.UpdateExchangeStatusCommand;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.UpdateExchangeStatusResource;

public class UpdateExchangeStatusCommandFromResourceAssembler {

            public static UpdateExchangeStatusCommand toCommandFromResource(Long exchangeId, UpdateExchangeStatusResource resource) {
                return new UpdateExchangeStatusCommand(exchangeId, resource.status());
            }
}
