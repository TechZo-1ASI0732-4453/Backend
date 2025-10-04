package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.application.internal.services.ExchangeAiService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ProductSuggestionResource;
import org.springframework.stereotype.Service;

@Service
public class GenerateAiSuggestionsCommandService {

    private final ExchangeAiService exchangeAiService;

    public GenerateAiSuggestionsCommandService(ExchangeAiService exchangeAiService) {
        this.exchangeAiService = exchangeAiService;
    }

    public ProductSuggestionResource suggestFromImage(byte[] image, String mimeType) {
        return exchangeAiService.suggestAllFromImage(image, mimeType);
    }
}
