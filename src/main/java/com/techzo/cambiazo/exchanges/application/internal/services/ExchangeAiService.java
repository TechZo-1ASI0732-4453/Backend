package com.techzo.cambiazo.exchanges.application.internal.services;

import com.techzo.cambiazo.exchanges.domain.ports.AiSuggestionPort;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ProductSuggestionResource;
import org.springframework.stereotype.Service;

@Service
public class ExchangeAiService {

    private final AiSuggestionPort aiSuggestionPort;

    public ExchangeAiService(AiSuggestionPort aiSuggestionPort) {
        this.aiSuggestionPort = aiSuggestionPort;
    }

    public ProductSuggestionResource suggestAllFromImage(byte[] image, String mimeType) {
        return aiSuggestionPort.suggestAllFromImage(image, mimeType);
    }
}
