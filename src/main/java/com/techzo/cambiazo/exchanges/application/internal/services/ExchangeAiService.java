package com.techzo.cambiazo.exchanges.application.internal.services;

import com.techzo.cambiazo.exchanges.domain.ports.AiSuggestionPort;
import org.springframework.stereotype.Service;

@Service
public class ExchangeAiService {

    public record ProductSuggestion(String name, String description, String price, String category) {}

    private final AiSuggestionPort aiSuggestionPort;

    public ExchangeAiService(AiSuggestionPort aiSuggestionPort) {
        this.aiSuggestionPort = aiSuggestionPort;
    }

    public ProductSuggestion suggestAllFromImage(byte[] image, String mimeType) {
        return aiSuggestionPort.suggestAllFromImage(image, mimeType);
    }
}
